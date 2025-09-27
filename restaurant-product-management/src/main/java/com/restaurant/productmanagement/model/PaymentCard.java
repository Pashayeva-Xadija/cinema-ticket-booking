package com.restaurant.productmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "payment_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class PaymentCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String last4;

    @Column(nullable = false)
    private Integer expMonth;

    @Column(nullable = false)
    private Integer expYear;

    private String providerToken;
    @Column(nullable = false)
    private Boolean defaultCard = false;


    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist void prePersist(){ this.createdAt = Instant.now(); }
}
