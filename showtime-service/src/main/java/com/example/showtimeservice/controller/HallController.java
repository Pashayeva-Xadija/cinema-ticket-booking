package com.example.showtimeservice.controller;

import com.example.showtimeservice.dto.HallDto;
import com.example.showtimeservice.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/halls")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HallDto create(@RequestBody HallDto dto) { return hallService.create(dto); }

    @GetMapping("/{id}")
    public HallDto get(@PathVariable Long id) { return hallService.get(id); }

    @GetMapping("/by-cinema/{cinemaId}")
    public List<HallDto> byCinema(@PathVariable Long cinemaId) { return hallService.listByCinema(cinemaId); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { hallService.delete(id); }
}
