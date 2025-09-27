package com.example.showtimeservice.service;

import com.example.showtimeservice.dto.HallDto;

import java.util.List;

public interface HallService {
    HallDto create(HallDto dto);
    HallDto get(Long id);
    List<HallDto> listByCinema(Long cinemaId);
    void delete(Long id);
}
