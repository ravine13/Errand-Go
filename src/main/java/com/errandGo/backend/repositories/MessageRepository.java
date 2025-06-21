package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT r FROM Message r WHERE r.id = :id")
    Optional<Message> findById(@Param("id") Long id);

}
