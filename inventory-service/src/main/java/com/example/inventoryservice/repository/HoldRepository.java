package com.example.inventoryservice.repository;

import com.example.inventoryservice.model.Hold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoldRepository extends JpaRepository<Hold, Long> {
}
