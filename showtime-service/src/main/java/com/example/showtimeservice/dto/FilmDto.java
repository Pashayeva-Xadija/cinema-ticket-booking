package com.example.showtimeservice.dto;

import com.example.showtimeservice.enumtype.Language;
import com.example.showtimeservice.enumtype.MovieFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FilmDto {
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private Language language;

    @NotNull
    private MovieFormat format;

    @NotNull
    @Positive
    private Integer durationMinutes;

    @Size(max = 1000)
    private String description;
}
