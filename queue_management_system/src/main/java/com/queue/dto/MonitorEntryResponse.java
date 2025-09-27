package com.queue.dto;

import com.queue.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonitorEntryResponse {
    private Long id;
    private String number;
    private String status;
    private String serviceType;
    private String desk;
}
