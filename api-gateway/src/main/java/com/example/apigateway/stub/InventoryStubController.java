package com.example.apigateway.stub;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryStubController {
    @GetMapping("/available")
    public Boolean isAvailable(@RequestParam Long showtimeId, @RequestParam Integer seatNumber){
        return true;
    }
}
