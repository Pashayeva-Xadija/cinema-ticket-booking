package com.queue.repository;

import com.queue.model.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeskRepository extends JpaRepository<Desk, Long> {
    boolean existsByName(String name);
}
