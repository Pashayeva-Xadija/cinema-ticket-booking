package com.example.showtimeservice.service;

import com.example.showtimeservice.dto.ShowtimeDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeService {
    ShowtimeDto create(ShowtimeDto dto);
    ShowtimeDto get(Long id);
    List<ShowtimeDto> byFilm(Long filmId);
    List<ShowtimeDto> byHall(Long hallId);
    List<ShowtimeDto> between(LocalDateTime from, LocalDateTime to);
    void delete(Long id);
}
