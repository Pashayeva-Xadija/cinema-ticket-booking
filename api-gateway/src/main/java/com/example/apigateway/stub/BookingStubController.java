package com.example.apigateway.stub;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/booking")
public class BookingStubController {
    @GetMapping("/demo")
    public Map<String, Object> demo() {
        return Map.of("service","booking","status","ok");
    }
}
