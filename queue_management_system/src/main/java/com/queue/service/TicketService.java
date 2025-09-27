package com.queue.service;

import com.queue.dto.CreateTicketRequest;
import com.queue.dto.MonitorEntryResponse;
import com.queue.dto.TicketResponse;

import java.util.List;

public interface TicketService {
    TicketResponse open(CreateTicketRequest req);
    TicketResponse callNext(Long deskId);
    TicketResponse start(Long ticketId);
    TicketResponse finish(Long ticketId);
    TicketResponse cancel(Long ticketId);
    List<MonitorEntryResponse> monitor();
}
