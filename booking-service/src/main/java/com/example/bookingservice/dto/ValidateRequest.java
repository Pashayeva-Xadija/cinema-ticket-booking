package com.example.bookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValidateRequest {
    @NotNull
    private Long bookingId;

    @NotBlank
    private String validationCode;
}