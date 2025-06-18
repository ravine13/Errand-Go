package com.errandGo.backend.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user; // user who gave the rating

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "errand_boy_id")
    private Account errandBoy; // errand boy who was rated
}
