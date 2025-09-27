package com.example.inventoryservice.serviceImpl;

import com.example.inventoryservice.service.SeatCacheService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SeatCacheServiceImpl implements SeatCacheService {

    private final StringRedisTemplate redisTemplate;

    public SeatCacheServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setSeatStatus(Long showtimeId, String seat, String status) {
        redisTemplate.opsForHash().put("showtime:" + showtimeId, seat, status);
    }

    @Override
    public String getSeatStatus(Long showtimeId, String seat) {
        return (String) redisTemplate.opsForHash().get("showtime:" + showtimeId, seat);
    }
}
