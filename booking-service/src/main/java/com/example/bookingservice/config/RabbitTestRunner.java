package com.example.bookingservice.config;

import com.example.bookingservice.messaging.BookingCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTestRunner {

    @Bean
    CommandLineRunner sendTest(RabbitTemplate rt,
                               @Value("${app.rabbit.exchange}") String ex,
                               @Value("${app.rabbit.routing}") String rk) {
        return args -> {
            try {
                BookingCreatedEvent evt = new BookingCreatedEvent(
                        1001L,
                        2002L,
                        3003L,
                        15,
                        "test@example.com"
                );
                rt.convertAndSend(ex, rk, evt);
                System.out.println(">>> Test BookingCreatedEvent sent to RabbitMQ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
