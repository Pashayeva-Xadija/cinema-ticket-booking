package com.example.ticketservice.serviceImpl;

import com.example.ticketservice.dto.TicketRequest;
import com.example.ticketservice.dto.TicketResponse;
import com.example.ticketservice.dto.ValidateRequest;
import com.example.ticketservice.enumtype.TicketStatus;
import com.example.ticketservice.exception.ConflictException;
import com.example.ticketservice.exception.NotFoundException;
import com.example.ticketservice.exception.ValidationException;
import com.example.ticketservice.mapper.TicketMapper;
import com.example.ticketservice.model.Ticket;
import com.example.ticketservice.repository.TicketRepository;
import com.example.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository repo;
    private final TicketMapper mapper;

    @Override
    @Transactional
    public TicketResponse create(TicketRequest request) {
        if (repo.existsByUserIdAndShowtimeIdAndSeatNumber(
                request.getUserId(), request.getShowtimeId(), request.getSeatNumber())) {
            throw new ConflictException("Ticket already exists for this user/showtime/seat");
        }

        Ticket t = mapper.toEntity(request);
        t.setStatus(TicketStatus.BOOKED);
        t.setValidationCode(UUID.randomUUID().toString());
        t.setBooked(true);
        t.setValidated(false);

        return mapper.toResponse(repo.save(t));
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponse getById(Long id) {
        Ticket t = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));
        return mapper.toResponse(t);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getAll() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional
    public TicketResponse validate(ValidateRequest request) {
        Ticket t = repo.findById(request.getTicketId())
                .orElseThrow(() -> new NotFoundException("Ticket not found"));

        if (!t.getValidationCode().equals(request.getValidationCode())) {
            throw new ValidationException("Invalid validation code");
        }

        t.setStatus(TicketStatus.USED);
        t.setValidated(true);

        return mapper.toResponse(repo.save(t));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Ticket not found");
        }
        repo.deleteById(id);
    }
}