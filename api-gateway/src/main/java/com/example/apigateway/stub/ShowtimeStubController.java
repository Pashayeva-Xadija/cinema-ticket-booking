// api-gateway/src/main/java/com/example/apigateway/stub/ShowtimeStubController.java
package com.example.apigateway.stub;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/showtime")
public class ShowtimeStubController {

    @GetMapping(value = "/public/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> list() {
        List<Map<String, Object>> items = List.of(
                Map.of("id", 101, "movie", "Inception", "startTime", "2025-09-28T20:00:00Z", "room", "Room 1", "price", 8.5),
                Map.of("id", 102, "movie", "Interstellar", "startTime", "2025-09-28T22:00:00Z", "room", "Room 2", "price", 9.0)
        );
        return Map.of("items", items, "total", items.size());
    }

    @GetMapping(value = "/public/seats/{showtimeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> seats(@PathVariable int showtimeId) {
        return Map.of(
                "showtimeId", showtimeId,
                "available", List.of("A1","A2","A3","B1","B2"),
                "taken", List.of("A4","B3")
        );
    }
}
