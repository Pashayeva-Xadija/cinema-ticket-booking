package com.example.showtimeservice.repository;

import com.example.showtimeservice.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {}
