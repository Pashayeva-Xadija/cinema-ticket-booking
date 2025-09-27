package com.example.inventoryservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "holds")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Hold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long showtimeId;
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "hold_seats", joinColumns = @JoinColumn(name = "hold_id"))
    @Column(name = "seat_number")
    private List<Integer> seatNumbers;

    private Instant createdAt;
    private Instant confirmedAt;
    private Instant releasedAt;
}
