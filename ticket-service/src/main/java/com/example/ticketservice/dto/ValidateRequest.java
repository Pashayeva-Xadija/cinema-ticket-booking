package com.example.ticketservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateRequest {
    @NotNull
    private Long ticketId;

    @NotNull
    private String validationCode;
}
