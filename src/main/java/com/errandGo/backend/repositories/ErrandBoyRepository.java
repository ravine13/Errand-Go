package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.ErrandBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ErrandBoyRepository extends JpaRepository<ErrandBoy, Long> {

    Optional<ErrandBoy> findByAccount_Username(String username);
    Optional<ErrandBoy> findByAccount_Email(String email);
    boolean existsByAccount_Username(String username);
    boolean existsByAccount_Email(String email);
}
