package com.example.bookingservice.serviceImpl;

import com.example.bookingservice.client.AuthClient;
import com.example.bookingservice.client.InventoryClient;
import com.example.bookingservice.dto.BookingRequest;
import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.dto.UserEmailResponse;
import com.example.bookingservice.dto.ValidateRequest;
import com.example.bookingservice.enumtype.TicketStatus;
import com.example.bookingservice.exception.ConflictException;
import com.example.bookingservice.exception.NotFoundException;
import com.example.bookingservice.exception.ServiceUnavailableException;
import com.example.bookingservice.exception.ValidationException;
import com.example.bookingservice.mapper.BookingMapper;
import com.example.bookingservice.messaging.BookingCreatedEvent;
import com.example.bookingservice.messaging.BookingEventPublisher;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repo;
    private final BookingMapper mapper;
    private final BookingEventPublisher publisher;
    private final InventoryClient inventoryClient;
    private final AuthClient authClient;

    @Override
    @Transactional
    public BookingResponse create(BookingRequest request) {
        assertSeatAvailable(request.getShowtimeId(), request.getSeatNumber());
        assertNotBooked(request.getShowtimeId(), request.getSeatNumber());

        Booking entity = newBooking(request);
        Booking saved = repo.save(entity);

        String email = safeFetchEmail(request.getUserId());
        publishCreatedEvent(saved, email);

        return mapper.toResponse(saved);
    }


    private void assertSeatAvailable(Long showtimeId, Integer seat) {
        Boolean available;
        try {
            available = inventoryClient.isSeatAvailable(showtimeId, seat);
            log.info("Seat check -> showtimeId={}, seat={}, available={}", showtimeId, seat, available);
        } catch (Exception ex) {
            throw new ServiceUnavailableException("Inventory service unavailable");
        }
        if (available == null || !available) {
            throw new ConflictException("Seat is not available");
        }
    }


    private void assertNotBooked(Long showtimeId, Integer seat) {
        if (repo.existsByShowtimeIdAndSeatNumber(showtimeId, seat)) {
            throw new ConflictException("Seat already booked for this showtime");
        }
    }

    private Booking newBooking(BookingRequest r) {
        Booking b = mapper.toEntity(r);
        b.setShowtimeId(r.getShowtimeId());
        b.setStatus(TicketStatus.BOOKED);
        b.setValidationCode(UUID.randomUUID().toString());
        return b;
    }

    private String safeFetchEmail(Long userId) {
        try {
            UserEmailResponse user = authClient.getEmail(userId);
            return user != null ? user.getEmail() : null;
        } catch (Exception ex) {
            log.warn("auth getEmail failed for userId={}", userId, ex);
            return null;
        }
    }

    private void publishCreatedEvent(Booking saved, String email) {
        BookingCreatedEvent evt = new BookingCreatedEvent(
                saved.getId(), saved.getTicketId(), saved.getUserId(), saved.getSeatNumber(), email
        );
        publisher.publish(evt);
        log.info("Published BookingCreatedEvent: {}", evt);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getById(Long id) {
        Booking b = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        String email = null;
        try {
            UserEmailResponse user = authClient.getEmail(b.getUserId());
            email = (user != null) ? user.getEmail() : null;
        } catch (Exception ex) {
            log.warn("auth-service getEmail failed for userId={}. Returning without email.", b.getUserId(), ex);
        }

        BookingResponse resp = mapper.toResponse(b);
        resp.setShowtimeId(b.getShowtimeId());
        resp.setUserEmail(email);
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getAll() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional
    public BookingResponse validate(ValidateRequest request) {
        Booking b = repo.findById(request.getBookingId())
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        if (!b.getValidationCode().equals(request.getValidationCode())) {
            throw new ValidationException("Invalid validation code");
        }
        b.setStatus(TicketStatus.USED);
        return mapper.toResponse(repo.save(b));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Booking not found");
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return repo.existsById(id);
    }
}
