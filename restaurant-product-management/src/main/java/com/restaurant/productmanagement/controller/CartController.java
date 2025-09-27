package com.restaurant.productmanagement.controller;

import com.restaurant.productmanagement.dto.AddToCartRequest;
import com.restaurant.productmanagement.dto.CartDto;
import com.restaurant.productmanagement.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getMyCart(Authentication a) {
        return ResponseEntity.ok(cartService.getMyCart(a.getName()));
    }

    @PostMapping("/items")
    public ResponseEntity<CartDto> addItem(@RequestBody AddToCartRequest req, Authentication a) {
        return ResponseEntity.ok(cartService.addItem(a.getName(), req));
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartDto> updateQuantity(@PathVariable Long productId,
                                                  @RequestParam int qty,
                                                  Authentication a) {
        return ResponseEntity.ok(cartService.updateQuantity(a.getName(), productId, qty));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartDto> removeItem(@PathVariable Long productId, Authentication a) {
        return ResponseEntity.ok(cartService.removeItem(a.getName(), productId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clear(Authentication a) {
        cartService.clear(a.getName());
        return ResponseEntity.noContent().build();
    }
}
