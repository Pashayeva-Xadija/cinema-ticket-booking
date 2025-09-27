package com.example.ticketservice.model;


import com.example.ticketservice.enumtype.TicketStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long showtimeId;
    private int seatNumber;
    private boolean booked;
    private boolean validated;
    private String validationCode;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;


}
