package com.queue.controller;

import com.queue.dto.MonitorEntryResponse;
import com.queue.dto.TicketResponse;
import com.queue.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitor")
@RequiredArgsConstructor
public class MonitorController {

    private final TicketService ticketService;

    @GetMapping
    public List<MonitorEntryResponse> list() {
        return ticketService.monitor();
    }
}
