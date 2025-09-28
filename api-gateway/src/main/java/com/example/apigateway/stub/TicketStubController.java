package com.example.apigateway.stub;

import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ticket")
public class TicketStubController {

    @PostMapping
    public Map<String, Object> create(@RequestBody Map<String, Object> req) {
        return Map.of(
                "ticketId", "t_" + Instant.now().toEpochMilli(),
                "showtimeId", req.getOrDefault("showtimeId", "show_1"),
                "seats", req.getOrDefault("seats", List.of("A1","A2")),
                "status", "ISSUED",
                "qr", "stub-qr-xxxx"
        );
    }

    @GetMapping("/{ticketId}")
    public Map<String, Object> get(@PathVariable String ticketId) {
        return Map.of(
                "ticketId", ticketId,
                "status", "ISSUED",
                "seats", List.of("A1","A2")
        );
    }

    @PostMapping("/{ticketId}/cancel")
    public Map<String, Object> cancel(@PathVariable String ticketId) {
        return Map.of("ticketId", ticketId, "status", "CANCELLED");
    }
}
