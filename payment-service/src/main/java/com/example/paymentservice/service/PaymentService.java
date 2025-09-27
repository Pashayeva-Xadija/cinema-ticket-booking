package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentInitRequest;
import com.example.paymentservice.dto.PaymentInitResponse;
import com.example.paymentservice.dto.PaymentWebhook;
import com.example.paymentservice.enumType.PaymentStatus;
import com.example.paymentservice.model.Payment;

public interface PaymentService {
    PaymentInitResponse init(PaymentInitRequest req);
    Payment get(Long id);
    PaymentStatus handleWebhook(PaymentWebhook payload);
}
