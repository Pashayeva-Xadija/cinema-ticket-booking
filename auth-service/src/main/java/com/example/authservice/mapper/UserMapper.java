package com.example.authservice.mapper;

import com.example.authservice.dto.UserResponse;
import com.example.authservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

    @Mapper(componentModel = "spring")
    public interface UserMapper {
        UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

        @Mapping(source = "role", target = "role")
        UserResponse toResponse(User user);
}