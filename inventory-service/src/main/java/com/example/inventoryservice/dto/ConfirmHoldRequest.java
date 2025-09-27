package com.example.inventoryservice.dto;

import lombok.Data;

@Data
public class ConfirmHoldRequest {
    private Long holdId;
    private Long userId;
}
