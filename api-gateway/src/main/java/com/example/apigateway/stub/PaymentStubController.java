package com.example.apigateway.stub;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentStubController {

    @GetMapping("/methods")
    public List<Map<String, Object>> methods() {
        return List.of(
                Map.of("code", "CARD", "name", "Bank Card"),
                Map.of("code", "APPLE_PAY", "name", "Apple Pay"),
                Map.of("code", "GOOGLE_PAY", "name", "Google Pay")
        );
    }

    @PostMapping("/pay")
    public Map<String, Object> pay(@RequestBody Map<String, Object> req) {
        return Map.of(
                "paymentId", "pay_" + Instant.now().toEpochMilli(),
                "amount", req.getOrDefault("amount", new BigDecimal("12.00")),
                "currency", req.getOrDefault("currency", "USD"),
                "status", "SUCCESS",
                "providerTxn", "stub-123456"
        );
    }

    @GetMapping("/status/{paymentId}")
    public Map<String, Object> status(@PathVariable String paymentId) {
        return Map.of("paymentId", paymentId, "status", "SUCCESS");
    }

    @PostMapping("/refund/{paymentId}")
    public ResponseEntity<Void> refund(@PathVariable String paymentId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
