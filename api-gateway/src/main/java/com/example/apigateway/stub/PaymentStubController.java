package com.example.apigateway.stub;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentStubController {
    @PostMapping("/init")
    public Map<String, Object> init(@RequestBody Map<String,Object> req){
        return Map.of("paymentId", 1, "status", "INITIATED", "redirectUrl","/mock-checkout/xyz");
    }
}
