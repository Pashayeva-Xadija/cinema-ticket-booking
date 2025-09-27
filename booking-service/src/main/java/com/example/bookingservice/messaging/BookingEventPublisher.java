package com.example.bookingservice.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbit.exchange}") private String exchange;
    @Value("${app.rabbit.routing}")  private String routingKey;

    public void publish(BookingCreatedEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}

