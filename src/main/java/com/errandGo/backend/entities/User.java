package com.errandGo.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user") // matches the database table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
