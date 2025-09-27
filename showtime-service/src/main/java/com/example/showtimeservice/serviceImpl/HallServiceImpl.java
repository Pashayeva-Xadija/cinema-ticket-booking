package com.example.showtimeservice.serviceImpl;

import com.example.showtimeservice.dto.HallDto;
import com.example.showtimeservice.exception.NotFoundException;
import com.example.showtimeservice.model.Cinema;
import com.example.showtimeservice.model.Hall;
import com.example.showtimeservice.repository.HallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements com.example.showtimeservice.service.HallService {

    private final HallRepository hallRepo;

    @Override
    public HallDto create(HallDto dto) {
        Hall h = Hall.builder()
                .name(dto.getName())
                .capacity(dto.getCapacity())
                .cinema(Cinema.builder().id(dto.getCinemaId()).build())
                .build();
        h = hallRepo.save(h);
        dto.setId(h.getId());
        return dto;
    }

    @Override
    public HallDto get(Long id) {
        Hall h = hallRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Hall not found"));
        HallDto dto = new HallDto();
        dto.setId(h.getId());
        dto.setName(h.getName());
        dto.setCapacity(h.getCapacity());
        dto.setCinemaId(h.getCinema().getId());
        return dto;
    }

    @Override
    public List<HallDto> listByCinema(Long cinemaId) {
        return hallRepo.findByCinema_Id(cinemaId).stream().map(h -> {
            HallDto dto = new HallDto();
            dto.setId(h.getId());
            dto.setName(h.getName());
            dto.setCapacity(h.getCapacity());
            dto.setCinemaId(h.getCinema().getId());
            return dto;
        }).toList();
    }

    @Override
    public void delete(Long id) {
        hallRepo.deleteById(id);
    }
}
