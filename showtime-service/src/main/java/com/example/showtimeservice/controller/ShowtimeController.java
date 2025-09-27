package com.example.showtimeservice.controller;

import com.example.showtimeservice.dto.ShowtimeDto;
import com.example.showtimeservice.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    @PostMapping
    public ShowtimeDto create(@RequestBody ShowtimeDto dto) { return showtimeService.create(dto); }

    @GetMapping("/{id}")
    public ShowtimeDto get(@PathVariable Long id) { return showtimeService.get(id); }

    @GetMapping("/by-film/{filmId}")
    public List<ShowtimeDto> byFilm(@PathVariable Long filmId) { return showtimeService.byFilm(filmId); }

    @GetMapping("/by-hall/{hallId}")
    public List<ShowtimeDto> byHall(@PathVariable Long hallId) { return showtimeService.byHall(hallId); }

    @GetMapping("/between")
    public List<ShowtimeDto> between(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return showtimeService.between(from, to);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { showtimeService.delete(id); }
}
