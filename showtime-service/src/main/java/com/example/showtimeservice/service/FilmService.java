package com.example.showtimeservice.service;

import com.example.showtimeservice.dto.FilmDto;
import java.util.List;

public interface FilmService {
    FilmDto create(FilmDto dto);
    FilmDto get(Long id);
    List<FilmDto> all();
    FilmDto update(Long id, FilmDto dto);
    void delete(Long id);
}
