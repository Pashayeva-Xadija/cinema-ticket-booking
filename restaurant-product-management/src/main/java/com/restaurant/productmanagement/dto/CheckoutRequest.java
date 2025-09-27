package com.restaurant.productmanagement.dto;

import com.restaurant.productmanagement.enums.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckoutRequest {
    private PaymentMethod paymentMethod;
    private String deliveryAddress;
    private BigDecimal deliveryFee;
}
