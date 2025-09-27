package com.queue.serviceImpl;

import com.queue.dto.DeskRequest;
import com.queue.dto.DeskResponse;
import com.queue.exception.ConflictException;
import com.queue.exception.NotFoundException;
import com.queue.mapper.DeskMapper;
import com.queue.model.Desk;
import com.queue.repository.DeskRepository;
import com.queue.service.DeskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeskServiceImpl implements DeskService {

    private final DeskRepository repo;
    private final DeskMapper mapper;

    @Override
    public DeskResponse create(DeskRequest req) {
        if (repo.existsByName(req.getName())) throw new ConflictException("Desk exists");
        Desk d = mapper.toEntity(req);
        return mapper.toDto(repo.save(d));
    }

    @Override
    public DeskResponse update(Long id, DeskRequest req) {
        Desk d = repo.findById(id).orElseThrow(() -> new NotFoundException("Desk not found"));
        mapper.update(d, req);
        return mapper.toDto(repo.save(d));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Desk not found");
        repo.deleteById(id);
    }

    @Override
    public List<DeskResponse> list() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public DeskResponse get(Long id) {
        return repo.findById(id).map(mapper::toDto).orElseThrow(() -> new NotFoundException("Desk not found"));
    }
}
