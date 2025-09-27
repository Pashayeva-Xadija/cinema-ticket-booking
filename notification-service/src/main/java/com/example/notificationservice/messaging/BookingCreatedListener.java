package com.example.notificationservice.messaging;


import com.example.notificationservice.dto.EmailRequest;
import com.example.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingCreatedListener {

    private final NotificationService notificationService;

    @Value("${app.rabbit.queue}")
    private String queueName;

    @RabbitListener(queues = "${app.rabbit.queue}")
    public void onMessage(BookingCreatedEvent event) {
        log.info("Received booking event: {}", event);

        if (event.getEmail() == null || event.getEmail().isBlank()) {
            log.warn("No email on event; skip sending");
            return;
        }

        EmailRequest req = EmailRequest.builder()
                .to(event.getEmail())
                .subject("Your booking is confirmed")
                .body("Booking #" + event.getBookingId() + " seat: " + event.getSeatNumber())
                .build();

        notificationService.sendEmail(req);
    }
}