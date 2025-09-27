package com.queue.controller;

import com.queue.dto.DeskRequest;
import com.queue.dto.DeskResponse;
import com.queue.service.DeskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/desks")
@RequiredArgsConstructor
public class DeskAdminController {

    private final DeskService service;

    @PostMapping
    public ResponseEntity<DeskResponse> create(@RequestBody @Valid DeskRequest req){
        return ResponseEntity.ok(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeskResponse> update(@PathVariable Long id, @RequestBody @Valid DeskRequest req){
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DeskResponse>> list(){
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeskResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(service.get(id));
    }
}
