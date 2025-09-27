package com.restaurant.productmanagement.service;

import com.restaurant.productmanagement.dto.AddCardRequest;
import com.restaurant.productmanagement.dto.PaymentCardDto;

import java.util.List;

public interface PaymentCardService {
    List<PaymentCardDto> myCards(String userEmail);
    PaymentCardDto addCard(String userEmail, AddCardRequest req);
    void remove(String userEmail, Long id);
    void setDefault(String userEmail, Long id);
}
