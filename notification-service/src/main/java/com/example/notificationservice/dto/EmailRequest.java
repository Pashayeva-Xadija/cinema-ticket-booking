package com.example.notificationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequest {
    @Email @NotBlank
    private String to;

    @NotBlank
    private String subject;

    @NotBlank
    private String body;
    private Long bookingId;
    private Integer seatNumber;
}
