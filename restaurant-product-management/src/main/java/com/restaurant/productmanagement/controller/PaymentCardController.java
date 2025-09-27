package com.restaurant.productmanagement.controller;

import com.restaurant.productmanagement.dto.AddCardRequest;
import com.restaurant.productmanagement.dto.PaymentCardDto;
import com.restaurant.productmanagement.service.PaymentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/account/cards")
@RequiredArgsConstructor
public class PaymentCardController {

    private final PaymentCardService service;

    @GetMapping
    public ResponseEntity<List<PaymentCardDto>> myCards(Principal p) {
        return ResponseEntity.ok(service.myCards(p.getName()));
    }

    @PostMapping
    public ResponseEntity<PaymentCardDto> add(Principal p, @RequestBody AddCardRequest req) {
        return ResponseEntity.ok(service.addCard(p.getName(), req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Principal p, @PathVariable Long id) {
        service.remove(p.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/default")
    public ResponseEntity<Void> setDefault(Principal p, @PathVariable Long id) {
        service.setDefault(p.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
