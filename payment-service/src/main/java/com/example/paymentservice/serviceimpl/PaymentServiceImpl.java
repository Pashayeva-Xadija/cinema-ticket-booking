package com.example.paymentservice.serviceimpl;

import com.example.paymentservice.client.BookingClient;
import com.example.paymentservice.client.TicketClient;
import com.example.paymentservice.client.NotificationClient;
import com.example.paymentservice.dto.PaymentInitRequest;
import com.example.paymentservice.dto.PaymentInitResponse;
import com.example.paymentservice.dto.PaymentWebhook;
import com.example.paymentservice.enumType.PaymentStatus;
import com.example.paymentservice.exception.NotFoundException;
import com.example.paymentservice.mapper.PaymentMapper;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repo;
    private final PaymentMapper mapper;
    private final BookingClient bookingClient;
    private final TicketClient ticketClient;
    private final NotificationClient notificationClient;

    @Override
    @Transactional
    public PaymentInitResponse init(PaymentInitRequest req) {
        try {
            Boolean exists = bookingClient.bookingExists(req.getBookingId());
            if (exists != null && !exists) {
                throw new NotFoundException("Booking not found");
            }
        } catch (Exception ignore) {  }

        Payment p = mapper.toEntity(req);
        p.setStatus(PaymentStatus.INITIATED);
        p.setProviderPaymentId(RandomStringUtils.randomAlphanumeric(20));
        p.setCreatedAt(OffsetDateTime.now());
        p.setUpdatedAt(OffsetDateTime.now());
        p = repo.save(p);

        String redirect = "/mock-checkout/" + p.getProviderPaymentId();
        return new PaymentInitResponse(p.getId(), p.getProviderPaymentId(), p.getStatus(), redirect);
    }

    @Override
    public Payment get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    @Override
    @Transactional
    public PaymentStatus handleWebhook(PaymentWebhook payload) {
        Payment p = requirePayment(payload.getProviderPaymentId());
        if (isDuplicatePaid(p, payload)) return p.getStatus();

        updateStatus(p, payload.getStatus());

        if (payload.getStatus() == PaymentStatus.PAID) {
            createTicketAndNotify(p);
        }
        return p.getStatus();
    }



    private Payment requirePayment(String providerPaymentId) {
        return repo.findByProviderPaymentId(providerPaymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    private boolean isDuplicatePaid(Payment p, PaymentWebhook payload) {
        return p.getStatus() == PaymentStatus.PAID && payload.getStatus() == PaymentStatus.PAID;
    }

    private void updateStatus(Payment p, PaymentStatus status) {
        p.setStatus(status);
        p.setUpdatedAt(OffsetDateTime.now());
        repo.save(p);
    }

    private void createTicketAndNotify(Payment p) {
        var log = org.slf4j.LoggerFactory.getLogger(getClass());
        BookingClient.BookingResponse br = bookingClient.getBooking(p.getBookingId());

        TicketClient.TicketRequest tr = new TicketClient.TicketRequest();
        tr.setUserId(br.getUserId());
        tr.setShowtimeId(br.getShowtimeId());
        tr.setSeatNumber(br.getSeatNumber());

        try {
            TicketClient.TicketResponse created = ticketClient.create(tr);
            maybeSendEmail(br.getUserEmail(), created.getId());
        } catch (Exception e) {
            log.warn("Ticket create/email failed (bookingId={}): {}", p.getBookingId(), e.getMessage(), e);
        }
    }

    private void maybeSendEmail(String email, Long ticketId) {
        if (email == null || email.isBlank()) return;
        NotificationClient.EmailRequest er = new NotificationClient.EmailRequest();
        er.setTo(email);
        er.setSubject("Your ticket is ready");
        er.setBody("Thanks! Your ticket ID: " + ticketId);
        notificationClient.sendEmail(er);
    }


}
