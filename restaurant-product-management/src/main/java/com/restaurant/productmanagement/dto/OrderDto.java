package com.restaurant.productmanagement.dto;

import com.restaurant.productmanagement.enums.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Instant createdAt;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private BigDecimal totalAmount;
    private List<CartItemDto> items;
    private String paymentProviderRef;
    private String deliveryAddress;
    private java.math.BigDecimal deliveryFee;
}
