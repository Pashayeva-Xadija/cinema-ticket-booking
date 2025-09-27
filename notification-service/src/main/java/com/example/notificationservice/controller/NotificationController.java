package com.example.notificationservice.controller;

import com.example.notificationservice.dto.EmailRequest;
import com.example.notificationservice.dto.SmsRequest;
import com.example.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")

public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/email")
    public ResponseEntity<Void> sendEmail(@Valid @RequestBody EmailRequest req) {
        service.sendEmail(req);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/sms")
    public ResponseEntity<Void> sendSms(@Valid @RequestBody SmsRequest req) {
        service.sendSms(req);
        return ResponseEntity.accepted().build();
    }
}
