package com.example.showtimeservice.controller;

import com.example.showtimeservice.dto.CinemaDto;
import com.example.showtimeservice.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CinemaDto create(@RequestBody CinemaDto dto) { return service.create(dto); }

    @GetMapping("/{id}")
    public CinemaDto get(@PathVariable Long id) { return service.get(id); }

    @GetMapping
    public List<CinemaDto> all() { return service.all(); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
