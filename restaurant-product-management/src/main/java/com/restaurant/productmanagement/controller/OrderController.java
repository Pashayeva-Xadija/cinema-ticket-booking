package com.restaurant.productmanagement.controller;

import com.restaurant.productmanagement.dto.CheckoutRequest;
import com.restaurant.productmanagement.dto.OrderDto;
import com.restaurant.productmanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkout(@RequestBody CheckoutRequest req,
                                             org.springframework.security.core.Authentication a) {
        return ResponseEntity.ok(orderService.checkout(a.getName(), req));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> myOrders(org.springframework.security.core.Authentication a) {
        return ResponseEntity.ok(orderService.myOrders(a.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id,
                                            org.springframework.security.core.Authentication a) {
        return ResponseEntity.ok(orderService.getById(id, a.getName()));
    }

    @PostMapping("/{id}/offline/paid")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<OrderDto> markOfflinePaid(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.markOfflinePaid(id));
    }
}
