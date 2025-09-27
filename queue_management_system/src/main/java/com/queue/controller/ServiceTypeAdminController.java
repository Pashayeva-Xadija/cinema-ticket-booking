package com.queue.controller;

import com.queue.dto.ServiceTypeRequest;
import com.queue.dto.ServiceTypeResponse;
import com.queue.service.ServiceTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/service-types")
@RequiredArgsConstructor
public class ServiceTypeAdminController {

    private final ServiceTypeService service;

    @PostMapping
    public ResponseEntity<ServiceTypeResponse> create(@RequestBody @Valid ServiceTypeRequest req){
        return ResponseEntity.ok(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceTypeResponse> update(@PathVariable Long id, @RequestBody @Valid ServiceTypeRequest req){
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ServiceTypeResponse>> list(){
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTypeResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(service.get(id));
    }
}
