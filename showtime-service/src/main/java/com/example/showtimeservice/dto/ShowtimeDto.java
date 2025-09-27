package com.example.showtimeservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShowtimeDto {
    private Long id;

    @NotNull
    private Long filmId;

    @NotNull
    private Long hallId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;
}
