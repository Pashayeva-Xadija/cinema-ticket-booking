package com.example.paymentservice.dto;

import com.example.paymentservice.enumType.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentInitRequest {
    @NotNull
    private Long bookingId;

    @NotNull @Min(1)
    private BigDecimal amount;

    @NotNull
    private PaymentMethod method;
}
