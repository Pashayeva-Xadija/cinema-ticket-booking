package com.example.paymentservice.dto;

import com.example.paymentservice.enumType.PaymentStatus;
import lombok.Data;

@Data
public class PaymentWebhook {
    private String providerPaymentId;
    private PaymentStatus status;
}
