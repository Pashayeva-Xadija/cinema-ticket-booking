package com.restaurant.productmanagement.service;

import com.restaurant.productmanagement.dto.CheckoutRequest;
import com.restaurant.productmanagement.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto checkout(String userEmail, CheckoutRequest request);
    List<OrderDto> myOrders(String userEmail);
    OrderDto getById(Long id, String userEmail);
    OrderDto markOfflinePaid(Long id);
}
