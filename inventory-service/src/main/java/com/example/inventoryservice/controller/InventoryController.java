package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.ConfirmHoldRequest;
import com.example.inventoryservice.dto.HoldRequest;
import com.example.inventoryservice.dto.HoldResponse;
import com.example.inventoryservice.enumtype.SeatState;
import com.example.inventoryservice.model.SeatStatus;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    @PostMapping("/hold")
    public ResponseEntity<HoldResponse> hold(@RequestBody HoldRequest req) {
        return ResponseEntity.ok(service.holdSeats(req));
    }

    @PostMapping("/confirm")
    public ResponseEntity<HoldResponse> confirm(@RequestBody ConfirmHoldRequest req) {
        return ResponseEntity.ok(service.confirmHold(req));
    }

    @PostMapping("/release/{holdId}")
    public ResponseEntity<Void> release(@PathVariable Long holdId) {
        service.releaseHold(holdId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/showtimes/{showtimeId}/seats")
    public ResponseEntity<List<SeatStatus>> seats(@PathVariable Long showtimeId, @RequestParam(required = false) SeatState state) {
        return ResponseEntity.ok(service.getSeats(showtimeId, state));
    }


    @GetMapping("/available")
    public Boolean isSeatAvailable(@RequestParam Long showtimeId, @RequestParam Integer seatNumber) {
        return service.isSeatAvailable(showtimeId, seatNumber);
    }

}