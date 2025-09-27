package com.queue.mapper;

import com.queue.dto.ServiceTypeRequest;
import com.queue.dto.ServiceTypeResponse;
import com.queue.model.ServiceType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceTypeMapper {
    ServiceType toEntity(ServiceTypeRequest req);
    ServiceTypeResponse toDto(ServiceType entity);
    void update(@MappingTarget ServiceType entity, ServiceTypeRequest req);
}
