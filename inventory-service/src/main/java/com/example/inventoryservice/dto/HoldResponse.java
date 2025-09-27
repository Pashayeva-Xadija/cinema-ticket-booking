package com.example.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class HoldResponse {
    private Long holdId;
    private Long showtimeId;
    private Long userId;
    private List<Integer> seatNumbers;
    private String status;
    private Instant createdAt;
}
