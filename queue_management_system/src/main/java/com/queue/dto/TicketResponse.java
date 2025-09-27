package com.queue.dto;

import com.queue.enums.TicketStatus;
import lombok.Data;

@Data
public class TicketResponse {
    private Long id;
    private String number;
    private TicketStatus status;
    private Long serviceTypeId;
    private Long deskId;
}
