package com.queue.serviceImpl;

import com.queue.dto.ServiceTypeRequest;
import com.queue.dto.ServiceTypeResponse;
import com.queue.exception.ConflictException;
import com.queue.exception.NotFoundException;
import com.queue.mapper.ServiceTypeMapper;
import com.queue.model.ServiceType;
import com.queue.repository.ServiceTypeRepository;
import com.queue.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final ServiceTypeRepository repo;
    private final ServiceTypeMapper mapper;

    @Override
    public ServiceTypeResponse create(ServiceTypeRequest req) {
        if (repo.existsByName(req.getName()))
            throw new ConflictException("Service type exists");


        String code = generateUniqueCode(req.getName());

        ServiceType st = mapper.toEntity(req);
        st.setCode(code);
        st.setLastSeq(0);

        return mapper.toDto(repo.save(st));
    }


    private String generateUniqueCode(String name) {
        String base = name == null ? "" : name.replaceAll("[^A-Za-z]", "").toUpperCase();
        if (base.isEmpty()) base = "S";


        for (int len = 1; len <= Math.min(3, base.length()); len++) {
            String candidate = base.substring(0, len);
            if (!repo.existsByCode(candidate)) return candidate;
        }

        for (int i = 2; i <= 9; i++) {
            String candidate = base.substring(0,1) + i;
            if (!repo.existsByCode(candidate)) return candidate;
        }
        throw new ConflictException("Cannot generate unique service code");
    }
    @Override
    public ServiceTypeResponse update(Long id, ServiceTypeRequest req) {
        ServiceType st = repo.findById(id).orElseThrow(() -> new NotFoundException("ServiceType not found"));
        mapper.update(st, req);
        return mapper.toDto(repo.save(st));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("ServiceType not found");
        repo.deleteById(id);
    }

    @Override
    public List<ServiceTypeResponse> list() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public ServiceTypeResponse get(Long id) {
        return repo.findById(id).map(mapper::toDto).orElseThrow(() -> new NotFoundException("ServiceType not found"));
    }
}
