package com.example.showtimeservice.mapper;

import com.example.showtimeservice.dto.ShowtimeDto;
import com.example.showtimeservice.model.Hall;
import com.example.showtimeservice.model.Film;
import com.example.showtimeservice.model.Showtime;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ShowtimeMapper {

    @Mappings({
            @Mapping(target = "film", source = "filmId", qualifiedByName = "filmFromId"),
            @Mapping(target = "hall", source = "hallId", qualifiedByName = "hallFromId")
    })
    Showtime toEntity(ShowtimeDto dto);

    @Mappings({
            @Mapping(target = "filmId", source = "film.id"),
            @Mapping(target = "hallId", source = "hall.id")
    })
    ShowtimeDto toDto(Showtime entity);

    @Named("filmFromId")
    default Film filmFromId(Long id){
        if (id == null) return null;
        Film f = new Film();
        f.setId(id);
        return f;
    }

    @Named("hallFromId")
    default Hall hallFromId(Long id){
        if (id == null) return null;
        Hall h = new Hall();
        h.setId(id);
        return h;
    }
}
