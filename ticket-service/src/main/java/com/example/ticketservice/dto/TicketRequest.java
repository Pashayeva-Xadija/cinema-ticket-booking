package com.example.ticketservice.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long showtimeId;

    @Min(1)
    private int seatNumber;
}
