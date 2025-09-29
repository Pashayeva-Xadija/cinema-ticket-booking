package com.example.notificationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Bean
    public NewTopic bookingCreatedTopic() {

        return new NewTopic("booking.created.v1", 1, (short)1);
    }
}
