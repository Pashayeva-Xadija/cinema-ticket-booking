package com.queue.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "services") // code üçün UNIQUE-i sütun səviyyəsində verəcəyik
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(length = 4, unique = true)
    private String code;

    @Version
    private Long version;


    @Column
    private Integer lastSeq;

    @ManyToMany(mappedBy = "serviceTypes", fetch = FetchType.LAZY)
    private Set<Desk> desks = new HashSet<>();
}
