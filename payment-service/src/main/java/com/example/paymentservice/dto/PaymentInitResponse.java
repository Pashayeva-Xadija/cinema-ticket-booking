package com.example.paymentservice.dto;

import com.example.paymentservice.enumType.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentInitResponse {
    private Long paymentId;
    private String providerPaymentId;
    private PaymentStatus status;
    private String redirectUrl;
}
