package com.example.paymentservice.controller;

import com.example.paymentservice.dto.PaymentInitRequest;
import com.example.paymentservice.dto.PaymentInitResponse;
import com.example.paymentservice.dto.PaymentWebhook;
import com.example.paymentservice.enumType.PaymentStatus;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/init")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentInitResponse init(@Valid @RequestBody PaymentInitRequest req) {
        return service.init(req);
    }

    @GetMapping("/{id}")
    public Payment get(@PathVariable Long id) {
        return service.get(id);
    }


    @PostMapping("/webhook")
    public PaymentStatus webhook(@RequestBody PaymentWebhook payload) {
        return service.handleWebhook(payload);
    }
}
