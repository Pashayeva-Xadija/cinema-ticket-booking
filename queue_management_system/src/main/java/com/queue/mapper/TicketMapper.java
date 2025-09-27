package com.queue.mapper;

import com.queue.dto.TicketResponse;
import com.queue.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "serviceTypeId", source = "serviceType.id")
    @Mapping(target = "deskId", source = "desk.id")
    TicketResponse toDto(Ticket entity);
}
