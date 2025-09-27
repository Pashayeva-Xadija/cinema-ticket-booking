package com.example.bookingservice.service;


import com.example.bookingservice.dto.BookingRequest;
import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.dto.ValidateRequest;

import java.util.List;

public interface BookingService {
    BookingResponse create(BookingRequest request);
    BookingResponse getById(Long id);
    List<BookingResponse> getAll();
    BookingResponse validate(ValidateRequest request);
    void delete(Long id);
    boolean exists(Long id);
}