package com.errandGo.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;
    private Double longitude;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, unique = true)
    private Account account;
}
