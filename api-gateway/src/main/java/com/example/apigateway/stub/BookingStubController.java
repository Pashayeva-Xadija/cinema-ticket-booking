// api-gateway/src/main/java/com/example/apigateway/stub/BookingStubController.java
package com.example.apigateway.stub;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/booking")
public class BookingStubController {

    public record ReserveRequest(Integer movieId, Integer showtimeId, List<String> seats) {}

    @PostMapping(value = "/reserve", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> reserve(@RequestBody ReserveRequest req) {
        String bookingId = UUID.randomUUID().toString();
        return Map.of(
                "status", "RESERVED",
                "bookingId", bookingId,
                "movieId", req.movieId(),
                "showtimeId", Optional.ofNullable(req.showtimeId()).orElse(101),
                "seats", req.seats(),
                "amount", 17.0
        );
    }
}
