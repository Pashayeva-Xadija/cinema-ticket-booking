package com.example.ticketservice.service;

import com.example.ticketservice.dto.TicketRequest;
import com.example.ticketservice.dto.TicketResponse;
import com.example.ticketservice.dto.ValidateRequest;

import java.util.List;

public interface TicketService {
    TicketResponse create(TicketRequest request);
    TicketResponse getById(Long id);
    List<TicketResponse> getAll();
    TicketResponse validate(ValidateRequest request);
    void delete(Long id);
}
