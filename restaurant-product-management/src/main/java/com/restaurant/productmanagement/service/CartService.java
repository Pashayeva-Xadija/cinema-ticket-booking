package com.restaurant.productmanagement.service;


import com.restaurant.productmanagement.dto.AddToCartRequest;
import com.restaurant.productmanagement.dto.CartDto;

public interface CartService {
    CartDto getMyCart(String userEmail);

    CartDto addItem(String userEmail, AddToCartRequest req);

    CartDto updateQuantity(String userEmail, Long productId, int quantity);

    CartDto removeItem(String userEmail, Long productId);

    void clear(String userEmail);
}
