package com.example.apigateway.stub;

import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notification")
public class NotificationStubController {

    @GetMapping
    public List<Map<String, Object>> list() {
        return List.of(
                Map.of("id","n1","type","EMAIL","to","demo@example.com","subject","Your tickets","time", Instant.now()),
                Map.of("id","n2","type","SMS","to","+123456789","subject","Payment success","time", Instant.now())
        );
    }

    @PostMapping("/send")
    public Map<String, Object> send(@RequestBody Map<String, Object> req) {
        return Map.of(
                "id", "n_"+Instant.now().toEpochMilli(),
                "accepted", true,
                "payload", req
        );
    }
}
