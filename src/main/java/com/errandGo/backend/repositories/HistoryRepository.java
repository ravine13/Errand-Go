package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History,Long> {
    @Query("SELECT r FROM History r WHERE r.id = :id")
    Optional<History> findById(@Param("id") Long id);
}
