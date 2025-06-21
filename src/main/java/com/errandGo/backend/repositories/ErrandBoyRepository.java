package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.ErrandBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ErrandBoyRepository extends JpaRepository<ErrandBoy, Long> {
    @Query("SELECT r FROM ErrandBoy r WHERE r.id = :id ")
    Optional<ErrandBoy> findById(@Param("id") Long id);
}
