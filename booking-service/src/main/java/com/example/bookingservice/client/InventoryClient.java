package com.example.bookingservice.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "${inventory.url:http://localhost:8081}")
public interface InventoryClient {

    @GetMapping("/api/inventory/available")
    Boolean isSeatAvailable(@RequestParam("showtimeId") Long showtimeId,
                            @RequestParam("seatNumber") Integer seatNumber);
}
