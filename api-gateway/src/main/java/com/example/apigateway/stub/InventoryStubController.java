// api-gateway/src/main/java/com/example/apigateway/stub/InventoryStubController.java
package com.example.apigateway.stub;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryStubController {

    @GetMapping(value="/health", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> health() {
        return Map.of("service","inventory","status","UP");
    }
}
