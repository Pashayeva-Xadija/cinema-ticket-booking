package com.queue.controller;

import com.queue.dto.CreateTicketRequest;
import com.queue.dto.TicketResponse;
import com.queue.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/open-ticket")
@RequiredArgsConstructor
public class OpenTicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> open(@RequestBody @Valid CreateTicketRequest req){
        return ResponseEntity.ok(ticketService.open(req));
    }
}
