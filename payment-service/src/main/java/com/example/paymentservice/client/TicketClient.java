package com.example.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "ticketClient", url = "${clients.ticket.base-url}")
public interface TicketClient {

    @PostMapping("/api/tickets")
    TicketResponse create(@RequestBody TicketRequest req);

    class TicketRequest {
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public Long getShowtimeId() { return showtimeId; }
        public void setShowtimeId(Long showtimeId) { this.showtimeId = showtimeId; }
        public int getSeatNumber() { return seatNumber; }
        public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
        private Long userId;
        private Long showtimeId;
        private int seatNumber;
    }

    class TicketResponse {
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        private Long id;

    }
}
