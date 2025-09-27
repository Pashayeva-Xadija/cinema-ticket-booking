package com.example.showtimeservice.dto;

import lombok.Data;

@Data
public class HallDto {
    private Long id;
    private String name;
    private Integer capacity;
    private Long cinemaId;
}
