package com.example.showtimeservice.repository;

import com.example.showtimeservice.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {}
