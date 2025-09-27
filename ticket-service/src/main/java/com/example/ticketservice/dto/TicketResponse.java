package com.example.ticketservice.dto;

import com.example.ticketservice.enumtype.TicketStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Long id;
    private Long userId;
    private Long showtimeId;
    private int seatNumber;
    private boolean booked;
    private boolean validated;
    private TicketStatus status;
}

