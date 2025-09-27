package com.example.showtimeservice.serviceImpl;

import com.example.showtimeservice.dto.ShowtimeDto;
import com.example.showtimeservice.exception.NotFoundException;
import com.example.showtimeservice.mapper.ShowtimeMapper;
import com.example.showtimeservice.model.Showtime;
import com.example.showtimeservice.repository.ShowtimeRepository;
import com.example.showtimeservice.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository repo;
    private final ShowtimeMapper mapper;

    @Override
    public ShowtimeDto create(ShowtimeDto dto) {
        Showtime entity = mapper.toEntity(dto);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public ShowtimeDto get(Long id) {
        Showtime s = repo.findById(id).orElseThrow(() -> new NotFoundException("Showtime not found"));
        return mapper.toDto(s);
    }

    @Override
    public List<ShowtimeDto> byFilm(Long filmId) {
        return repo.findByFilm_Id(filmId).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<ShowtimeDto> byHall(Long hallId) {
        return repo.findByHall_Id(hallId).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<ShowtimeDto> between(LocalDateTime from, LocalDateTime to) {
        return repo.findByStartTimeBetween(from, to).stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) { repo.deleteById(id); }
}
