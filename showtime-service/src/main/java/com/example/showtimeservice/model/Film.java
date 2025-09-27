package com.example.showtimeservice.model;

import com.example.showtimeservice.enumtype.Language;
import com.example.showtimeservice.enumtype.MovieFormat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "films")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Film {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String title;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Language language;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private MovieFormat format;

    @Column(nullable = false) private Integer durationMinutes; // 90, 120...
    @Column(length = 1000) private String description;
}
