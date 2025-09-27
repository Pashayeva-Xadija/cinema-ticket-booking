package com.queue.service;

import com.queue.dto.ServiceTypeRequest;
import com.queue.dto.ServiceTypeResponse;

import java.util.List;

public interface ServiceTypeService {
    ServiceTypeResponse create(ServiceTypeRequest req);
    ServiceTypeResponse update(Long id, ServiceTypeRequest req);
    void delete(Long id);
    List<ServiceTypeResponse> list();
    ServiceTypeResponse get(Long id);
}
