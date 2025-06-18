package com.errandGo.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "device_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key relationship to the Account table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "device_token", columnDefinition = "TEXT")
    private String deviceToken;
}
