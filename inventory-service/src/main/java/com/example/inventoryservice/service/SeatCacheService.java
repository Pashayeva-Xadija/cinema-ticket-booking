package com.example.inventoryservice.service;

public interface SeatCacheService {
    void setSeatStatus(Long showtimeId, String seat, String status);
    String getSeatStatus(Long showtimeId, String seat);
}
