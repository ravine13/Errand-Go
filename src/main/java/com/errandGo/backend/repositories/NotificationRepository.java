package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Query("SELECT r FROM Notification r WHERE r.id = :id")
    Optional<Notification> findById(@Param("id") Long id);
}
