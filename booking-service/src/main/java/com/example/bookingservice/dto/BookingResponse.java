package com.example.bookingservice.dto;

import com.example.bookingservice.enumtype.TicketStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long ticketId;
    private Long userId;
    private Integer seatNumber;
    private TicketStatus status;
    private String validationCode;
    private Long showtimeId;
    private String userEmail;
}