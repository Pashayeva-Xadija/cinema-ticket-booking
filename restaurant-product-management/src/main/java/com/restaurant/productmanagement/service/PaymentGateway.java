
package com.restaurant.productmanagement.service;

import java.math.BigDecimal;

public interface PaymentGateway {
    String createPayment(BigDecimal amount, String description);

    default String tokenizeCard(String cardNumber, int expMonth, int expYear, String cvc) {
        return "CARD-" + java.util.UUID.randomUUID();
    }
}