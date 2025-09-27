package com.example.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bookingClient", url = "${clients.booking.base-url}")
public interface BookingClient {

    @GetMapping("/api/bookings/{id}")
    BookingResponse getBooking(@PathVariable("id") Long id);

    @GetMapping("/api/bookings/{id}/exists")
    Boolean bookingExists(@PathVariable("id") Long bookingId);

    class BookingResponse {
        private Long id;
        private Long ticketId;
        private Long userId;
        private Integer seatNumber;
        private String status;
        private String validationCode;

        private Long showtimeId;
        private String userEmail;


        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public Long getTicketId() { return ticketId; }
        public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public Integer getSeatNumber() { return seatNumber; }
        public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getValidationCode() { return validationCode; }
        public void setValidationCode(String validationCode) { this.validationCode = validationCode; }


        public Long getShowtimeId() { return showtimeId; }
        public void setShowtimeId(Long showtimeId) { this.showtimeId = showtimeId; }

        public String getUserEmail() { return userEmail; }
        public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    }
}
