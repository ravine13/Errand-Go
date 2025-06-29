package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {
    @Query("SELECT r FROM Rating r WHERE r.id = :id")
    Optional<Rating> findById(@Param("id") Long id);
    List<Rating> findByErrandBoyId(Long errandBoyId);
    List<Rating>findByUserId(Long userId);
}
