package com.example.showtimeservice.controller;

import com.example.showtimeservice.dto.FilmDto;
import com.example.showtimeservice.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDto create(@RequestBody FilmDto dto) { return service.create(dto); }

    @GetMapping("/{id}")
    public FilmDto get(@PathVariable Long id) { return service.get(id); }

    @GetMapping
    public List<FilmDto> all() { return service.all(); }

    @PutMapping("/{id}")
    public FilmDto update(@PathVariable Long id, @RequestBody FilmDto dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
