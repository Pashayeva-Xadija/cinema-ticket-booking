package com.example.showtimeservice.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cinemas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cinema {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String city;
    @Column(nullable = false) private String address;
}