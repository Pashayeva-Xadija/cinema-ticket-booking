package com.queue.repository;

import com.queue.enums.TicketStatus;
import com.queue.model.ServiceType;
import com.queue.model.Ticket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByStatus(TicketStatus status);

    List<Ticket> findAllByServiceTypeAndStatusOrderByIdAsc(ServiceType serviceType, TicketStatus status);

    Optional<Ticket> findTopByServiceTypeAndStatusOrderByIdAsc(ServiceType serviceType, TicketStatus status);

    boolean existsByNumber(String number);

    @EntityGraph(attributePaths = {"serviceType", "desk"})
    List<Ticket> findAllByOrderByIdDesc();
}
