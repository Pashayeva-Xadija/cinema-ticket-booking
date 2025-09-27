package com.example.bookingservice.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbit.exchange}")
    private String exchangeName;
    @Value("${app.rabbit.queue}")
    private String queueName;
    @Value("${app.rabbit.routing}")
    private String routingKey;

    @Bean
    public DirectExchange bookingExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding bindQueueToExchange(Queue notificationQueue, DirectExchange bookingExchange) {
        return BindingBuilder.bind(notificationQueue).to(bookingExchange).with(routingKey);
    }
}
