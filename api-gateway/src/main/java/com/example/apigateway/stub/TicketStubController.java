package com.example.apigateway.stub;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ticket")
public class TicketStubController {
    @GetMapping("/demo")
    public Map<String, Object> demo() {
        return Map.of("service","ticket","status","ok");
    }
}
