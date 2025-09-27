package com.queue.mapper;

import com.queue.dto.DeskRequest;
import com.queue.dto.DeskResponse;
import com.queue.model.Desk;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeskMapper {
    Desk toEntity(DeskRequest req);
    DeskResponse toDto(Desk entity);
    void update(@MappingTarget Desk entity, DeskRequest req);
}
