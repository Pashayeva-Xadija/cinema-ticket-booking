package com.queue.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "desks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "desk_service_types",
            joinColumns = @JoinColumn(name = "desk_id"),
            inverseJoinColumns = @JoinColumn(name = "service_type_id")
    )
    @Builder.Default
    private Set<ServiceType> serviceTypes = new HashSet<>();
}
