package com.queue.service;

import com.queue.dto.DeskRequest;
import com.queue.dto.DeskResponse;

import java.util.List;

public interface DeskService {
    DeskResponse create(DeskRequest req);
    DeskResponse update(Long id, DeskRequest req);
    void delete(Long id);
    List<DeskResponse> list();
    DeskResponse get(Long id);
}
