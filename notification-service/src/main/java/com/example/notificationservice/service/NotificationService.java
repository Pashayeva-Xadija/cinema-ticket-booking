package com.example.notificationservice.service;

import com.example.notificationservice.dto.EmailRequest;
import com.example.notificationservice.dto.SmsRequest;

public interface NotificationService {
    void sendEmail(EmailRequest request);
    void sendSms(SmsRequest request);
}
