package com.example.showtimeservice.service;
import com.example.showtimeservice.dto.CinemaDto;
import java.util.List;

public interface CinemaService {
    CinemaDto create(CinemaDto dto);
    CinemaDto get(Long id);
    List<CinemaDto> all();
    void delete(Long id);
}
