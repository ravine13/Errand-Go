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

    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 120)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Double latitude;
    private Double longitude;

    public enum Role {
        USER,
        ERRAND_BOY,
        ADMIN
    }
}
