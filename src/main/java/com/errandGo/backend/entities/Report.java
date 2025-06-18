package com.errandGo.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    // The user who reported
    @ManyToOne
    @JoinColumn(name = "reported_by_id")
    private Account reportedBy;

    // The user being reported
    @ManyToOne
    @JoinColumn(name = "reported_against_id")
    private Account reportedAgainst;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}