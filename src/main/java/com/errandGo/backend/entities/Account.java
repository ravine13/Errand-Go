package com.errandGo.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import model.Role;

import java.time.Instant;

@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(length = 120)
    private String location;

    @Column(name = "device_token")
    private String deviceToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    @Column(name = "token_valid_after")
    private Instant tokenValidAfter;

    @Column(name = "must_change_password", nullable = false)
    private boolean mustChangePassword = false;

}
