package com.queue.serviceImpl;

import com.queue.dto.CreateTicketRequest;
import com.queue.dto.MonitorEntryResponse;
import com.queue.dto.TicketResponse;
import com.queue.enums.TicketStatus;
import com.queue.exception.BadRequestException;
import com.queue.exception.NotFoundException;
import com.queue.mapper.TicketMapper;
import com.queue.model.Desk;
import com.queue.model.ServiceType;
import com.queue.model.Ticket;
import com.queue.repository.DeskRepository;
import com.queue.repository.ServiceTypeRepository;
import com.queue.repository.TicketRepository;
import com.queue.service.TicketService;
import com.queue.util.TicketNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepo;
    private final ServiceTypeRepository serviceTypeRepo;
    private final DeskRepository deskRepo;
    private final TicketMapper mapper;

    @Override
    @Transactional
    public TicketResponse open(CreateTicketRequest req) {
        ServiceType st = serviceTypeRepo.findById(req.getServiceTypeId())
                .orElseThrow(() -> new NotFoundException("ServiceType not found"));


        String code = st.getCode();
        if (code == null || code.isBlank()) {
            String base = st.getName() == null ? "" : st.getName().replaceAll("[^A-Za-z]", "").toUpperCase();
            if (base.isEmpty()) base = "S";
            int len = Math.min(3, base.length());
            code = base.substring(0, len);
            st.setCode(code);
        }


        Integer seq = st.getLastSeq();
        if (seq == null) seq = 0;
        seq++;
        st.setLastSeq(seq);

        String number = code + String.format("%03d", seq);


        Ticket t = Ticket.builder()
                .number(number)
                .status(TicketStatus.WAITING)
                .serviceType(st)
                .build();

        t = ticketRepo.save(t);
        return mapper.toDto(t);
    }



    @Override
    @Transactional
    public TicketResponse callNext(Long deskId) {
        Desk desk = deskRepo.findById(deskId).orElseThrow(() -> new NotFoundException("Desk not found"));
        Ticket t = ticketRepo.findAllByStatus(TicketStatus.WAITING).stream().findFirst()
                .orElseThrow(() -> new BadRequestException("No open tickets"));
        t.setDesk(desk);
        t.setStatus(TicketStatus.CALLED);
        return mapper.toDto(t);
    }

    @Override
    public TicketResponse start(Long ticketId) {
        Ticket t = getTicket(ticketId);
        t.setStatus(TicketStatus.IN_PROGRESS);
        return mapper.toDto(ticketRepo.save(t));
    }

    @Override
    public TicketResponse finish(Long ticketId) {
        Ticket t = getTicket(ticketId);
        t.setStatus(TicketStatus.FINISHED);
        return mapper.toDto(ticketRepo.save(t));
    }

    @Override
    public TicketResponse cancel(Long ticketId) {
        Ticket t = getTicket(ticketId);
        t.setStatus(TicketStatus.CANCELED);
        return mapper.toDto(ticketRepo.save(t));
    }


    @Override
    @Transactional(readOnly = true)
    public List<MonitorEntryResponse> monitor() {
        return ticketRepo.findAllByOrderByIdDesc()
                .stream()
                .map(t -> new MonitorEntryResponse(
                        t.getId(),
                        t.getNumber(),
                        t.getStatus().name(),
                        t.getServiceType() != null ? t.getServiceType().getName() : "-",
                        t.getDesk() != null
                                ? (t.getDesk().getName() != null ? t.getDesk().getName() : String.valueOf(t.getDesk().getId()))
                                : "-"
                ))
                .toList();
    }

    private Ticket getTicket(Long id){
        return ticketRepo.findById(id).orElseThrow(() -> new NotFoundException("Ticket not found"));
    }
}
