package com.example.showtimeservice.serviceImpl;

import com.example.showtimeservice.dto.FilmDto;
import com.example.showtimeservice.exception.NotFoundException;
import com.example.showtimeservice.mapper.FilmMapper;
import com.example.showtimeservice.model.Film;
import com.example.showtimeservice.repository.FilmRepository;
import com.example.showtimeservice.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository repo;
    private final FilmMapper mapper;

    @Override
    public FilmDto create(FilmDto dto) {
        Film saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    public FilmDto get(Long id) {
        Film f = repo.findById(id).orElseThrow(() -> new NotFoundException("Film not found"));
        return mapper.toDto(f);
    }

    @Override
    public List<FilmDto> all() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public FilmDto update(Long id, FilmDto dto) {
        Film f = repo.findById(id).orElseThrow(() -> new NotFoundException("Film not found"));
        f.setTitle(dto.getTitle());
        f.setLanguage(dto.getLanguage());
        f.setFormat(dto.getFormat());
        f.setDurationMinutes(dto.getDurationMinutes());
        f.setDescription(dto.getDescription());
        return mapper.toDto(repo.save(f));
    }

    @Override
    public void delete(Long id) { repo.deleteById(id); }
}

