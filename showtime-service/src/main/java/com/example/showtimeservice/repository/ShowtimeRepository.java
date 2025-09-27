package com.example.showtimeservice.repository;

import com.example.showtimeservice.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByHall_Id(Long hallId);
    List<Showtime> findByFilm_Id(Long filmId);
    List<Showtime> findByStartTimeBetween(LocalDateTime from, LocalDateTime to);
}
