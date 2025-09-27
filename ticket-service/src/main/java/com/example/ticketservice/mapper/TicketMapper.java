package com.example.ticketservice.mapper;

import com.example.ticketservice.dto.TicketRequest;
import com.example.ticketservice.dto.TicketResponse;
import com.example.ticketservice.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "validationCode", ignore = true)
    @Mapping(target = "booked", ignore = true)
    @Mapping(target = "validated", ignore = true)
    Ticket toEntity(TicketRequest req);

    TicketResponse toResponse(Ticket entity);
}
