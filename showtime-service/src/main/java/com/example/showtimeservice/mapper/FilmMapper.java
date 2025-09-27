package com.example.showtimeservice.mapper;

import com.example.showtimeservice.dto.FilmDto;
import com.example.showtimeservice.model.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmMapper {
    FilmDto toDto(Film entity);
    Film toEntity(FilmDto dto);
}
