package com.example.showtimeservice.serviceImpl;
import com.example.showtimeservice.dto.CinemaDto;
import com.example.showtimeservice.exception.NotFoundException;
import com.example.showtimeservice.model.Cinema;
import com.example.showtimeservice.repository.CinemaRepository;
import com.example.showtimeservice.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository repo;

    @Override
    public CinemaDto create(CinemaDto dto) {
        Cinema c = Cinema.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .address(dto.getAddress())
                .build();
        c = repo.save(c);
        dto.setId(c.getId());
        return dto;
    }

    @Override
    public CinemaDto get(Long id) {
        Cinema c = repo.findById(id).orElseThrow(() -> new NotFoundException("Cinema not found"));
        CinemaDto dto = new CinemaDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setCity(c.getCity());
        dto.setAddress(c.getAddress());
        return dto;
    }

    @Override
    public List<CinemaDto> all() {
        return repo.findAll().stream().map(c -> {
            CinemaDto dto = new CinemaDto();
            dto.setId(c.getId());
            dto.setName(c.getName());
            dto.setCity(c.getCity());
            dto.setAddress(c.getAddress());
            return dto;
        }).toList();
    }

    @Override
    public void delete(Long id) { repo.deleteById(id); }
}
