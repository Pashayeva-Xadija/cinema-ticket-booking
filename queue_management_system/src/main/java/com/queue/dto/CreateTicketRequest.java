package com.queue.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTicketRequest {
    @NotNull
    private Long serviceTypeId;
}
