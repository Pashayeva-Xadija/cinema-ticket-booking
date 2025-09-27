package com.example.inventoryservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class HoldRequest {
    private Long showtimeId;
    private Long userId;
    private List<Integer> seatNumbers;
}
