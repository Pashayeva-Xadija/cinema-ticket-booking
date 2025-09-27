package com.queue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceTypeRequest {
    @NotBlank
    private String name;
}
