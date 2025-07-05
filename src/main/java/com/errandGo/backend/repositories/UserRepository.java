package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccount_Username(String username);
    Optional<User> findByAccount_Email(String email);

    boolean existsByAccount_Username(String username);
    boolean existsByAccount_Email(String email);
}
