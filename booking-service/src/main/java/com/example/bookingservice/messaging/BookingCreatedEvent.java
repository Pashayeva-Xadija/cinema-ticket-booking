package com.example.bookingservice.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BookingCreatedEvent {
    private Long bookingId;
    private Long ticketId;
    private Long userId;
    private Integer seatNumber;
    private String email;
}
