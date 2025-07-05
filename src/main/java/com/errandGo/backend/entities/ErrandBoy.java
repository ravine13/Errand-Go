package com.errandGo.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Boolean isOnline = false;

    private Integer totalCompletedTasks = 0;

    private Float averageRating = 0f;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, unique = true)
    private Account account;

    // Optional: Add convenience methods to access common account info
    @JsonIgnore
    public String getEmail() {
        return account.getEmail();
    }

    @JsonIgnore
    public String getFullName() {
        return account.getUsername();
    }
}
