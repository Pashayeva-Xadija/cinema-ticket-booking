package com.example.bookingservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
    @NotNull
    private Long ticketId;

    @NotNull
    private Long userId;

    @Min(1)
    private Integer seatNumber;

    @NotNull
    private Long showtimeId;
}
