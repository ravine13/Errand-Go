package com.errandGo.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "errand_boy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrandBoy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_online")
    private Boolean isOnline = false;

    @Column(name = "total_completed_tasks")
    private Integer totalCompletedTasks = 0;

    @Column(name = "average_rating")
    private Float averageRating = 0f;

}