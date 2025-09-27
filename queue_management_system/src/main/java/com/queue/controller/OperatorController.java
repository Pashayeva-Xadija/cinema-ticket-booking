package com.queue.controller;

import com.queue.dto.TicketResponse;
import com.queue.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operator")
@RequiredArgsConstructor
public class OperatorController {

    private final TicketService ticketService;

    @PostMapping("/desks/{deskId}/call-next")
    public ResponseEntity<TicketResponse> callNext(@PathVariable Long deskId){
        return ResponseEntity.ok(ticketService.callNext(deskId));
    }

    @PostMapping("/tickets/{id}/start")
    public ResponseEntity<TicketResponse> start(@PathVariable Long id){
        return ResponseEntity.ok(ticketService.start(id));
    }

    @PostMapping("/tickets/{id}/finish")
    public ResponseEntity<TicketResponse> finish(@PathVariable Long id){
        return ResponseEntity.ok(ticketService.finish(id));
    }

    @PostMapping("/tickets/{id}/cancel")
    public ResponseEntity<TicketResponse> cancel(@PathVariable Long id){
        return ResponseEntity.ok(ticketService.cancel(id));
    }
}
