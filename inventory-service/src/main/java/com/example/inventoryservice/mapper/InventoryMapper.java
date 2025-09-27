package com.example.inventoryservice.mapper;

import com.example.inventoryservice.dto.HoldResponse;
import com.example.inventoryservice.model.Hold;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(target = "status", expression = "java(hold.getReleasedAt() != null ? \"RELEASED\" : (hold.getConfirmedAt() != null ? \"CONFIRMED\" : \"HELD\"))")
    HoldResponse toResponse(Hold hold);
}
