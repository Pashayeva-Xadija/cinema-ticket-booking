package com.example.inventoryservice.repository;

import com.example.inventoryservice.enumtype.SeatState;
import com.example.inventoryservice.model.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatStatusRepository extends JpaRepository<SeatStatus, Long> {

    List<SeatStatus> findByShowtimeId(Long showtimeId);

    List<SeatStatus> findByShowtimeIdAndState(Long showtimeId, SeatState state);

    Optional<SeatStatus> findByShowtimeIdAndSeatNumber(Long showtimeId, Integer seatNumber);
}
