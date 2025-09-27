package com.example.notificationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsRequest {
    @NotBlank
    private String phone;
    @NotBlank
    private String message;
}
