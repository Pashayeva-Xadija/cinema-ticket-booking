package com.example.bookingservice.config;

import com.example.bookingservice.messaging.BookingCreatedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap;

    public static final String BOOKING_TOPIC = "booking.created.v1";

    @Bean
    public ProducerFactory<String, BookingCreatedEvent> bookingProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return new DefaultKafkaProducerFactory<>(
                props,
                new org.apache.kafka.common.serialization.StringSerializer(),
                new JsonSerializer<>()
        );
    }

    @Bean
    public KafkaTemplate<String, BookingCreatedEvent> bookingKafkaTemplate() {
        return new KafkaTemplate<>(bookingProducerFactory());
    }


    @Bean
    public NewTopic bookingCreatedTopic() {
        return new NewTopic(BOOKING_TOPIC, 1, (short) 1);
    }
}
