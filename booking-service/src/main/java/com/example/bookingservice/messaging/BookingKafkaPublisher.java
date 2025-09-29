package com.example.bookingservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.example.bookingservice.config.KafkaConfig.BOOKING_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingKafkaPublisher {

    private final KafkaTemplate<String, BookingCreatedEvent> template;

    public void publish(BookingCreatedEvent event) {
        template.send(BOOKING_TOPIC, event.getBookingId().toString(), event)
                .whenComplete((res, ex) -> {
                    if (ex != null) log.error("Kafka publish failed", ex);
                    else log.info("Kafka published to {} partition={}, offset={}",
                            res.getRecordMetadata().topic(),
                            res.getRecordMetadata().partition(),
                            res.getRecordMetadata().offset());
                });
    }
}
