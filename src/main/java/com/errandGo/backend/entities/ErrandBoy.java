package com.errandGo.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "errand_boy")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrandBoy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(name = "is_online")
    private Boolean isOnline = false;

    @Column(name = "total_completed_tasks")
    private Integer totalCompletedTasks = 0;

    @Column(name = "average_rating")
    private Float averageRating = 0f;

    @JsonProperty("password") // explicitly map password
    private String password;
}