package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.ConfirmHoldRequest;
import com.example.inventoryservice.dto.HoldRequest;
import com.example.inventoryservice.dto.HoldResponse;
import com.example.inventoryservice.enumtype.SeatState;
import com.example.inventoryservice.model.SeatStatus;

import java.util.List;

public interface InventoryService {
    HoldResponse holdSeats(HoldRequest request);
    HoldResponse confirmHold(ConfirmHoldRequest request);
    void releaseHold(Long holdId);
    List<SeatStatus> getSeats(Long showtimeId, SeatState state);
    Boolean isSeatAvailable(Long showtimeId, Integer seatNumber);
}
