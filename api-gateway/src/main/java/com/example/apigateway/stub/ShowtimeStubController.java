package com.example.apigateway.stub;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/showtime")
public class ShowtimeStubController {
    @GetMapping("/by-film/{filmId}")
    public List<Map<String,Object>> byFilm(@PathVariable Long filmId){
        return List.of(Map.of("id",1,"filmId",filmId,"hallId",10,"price","12.00"));
    }
}
