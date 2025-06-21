package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Query("SELECT r FROM Payment r WHERE r.id = :id")
    Optional<Payment> findById(@Param("id") Long id);
}
