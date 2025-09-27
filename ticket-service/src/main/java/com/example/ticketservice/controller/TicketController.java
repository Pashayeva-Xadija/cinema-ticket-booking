package com.example.ticketservice.controller;

import com.example.ticketservice.dto.TicketRequest;
import com.example.ticketservice.dto.TicketResponse;
import com.example.ticketservice.dto.ValidateRequest;
import com.example.ticketservice.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    @PostMapping
    public ResponseEntity<TicketResponse> create(@RequestBody @Valid TicketRequest req) {
        return ResponseEntity.status(201).body(service.create(req));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/validate")
    public ResponseEntity<TicketResponse> validate(@RequestBody @Valid ValidateRequest req) {
        return ResponseEntity.ok(service.validate(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
