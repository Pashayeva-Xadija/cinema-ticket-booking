package com.example.notificationservice.messaging;

import com.example.notificationservice.dto.EmailRequest;
import com.example.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingCreatedKafkaListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "booking.created.v1", groupId = "notification-service")
    public void onMessage(BookingCreatedEvent event) {
        log.info("[Kafka] received booking event: {}", event);
        if (event.getEmail() == null || event.getEmail().isBlank()) return;

        EmailRequest req = EmailRequest.builder()
                .to(event.getEmail())
                .subject("Your booking is confirmed (Kafka)")
                .body("Booking #" + event.getBookingId() + " seat: " + event.getSeatNumber())
                .build();
        notificationService.sendEmail(req);
    }
}
