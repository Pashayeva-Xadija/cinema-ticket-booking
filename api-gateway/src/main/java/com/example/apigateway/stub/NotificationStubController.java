package com.example.apigateway.stub;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/notification")
public class NotificationStubController {
    @PostMapping("/email")
    public Map<String,String> email(@RequestBody Map<String,String> req){
        return Map.of("result","email-sent (demo)");
    }
}
