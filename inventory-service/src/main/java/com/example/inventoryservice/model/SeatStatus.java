package com.example.inventoryservice.model;

import com.example.inventoryservice.enumtype.SeatState;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat_status",
        uniqueConstraints = @UniqueConstraint(columnNames = {"showtime_id", "seat_number"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SeatStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "showtime_id", nullable = false)
    private Long showtimeId;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatState state;
}
