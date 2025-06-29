package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.TaskAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAttemptRepository extends JpaRepository<TaskAttempt, Long> {
    @Query("SELECT r FROM TaskAttempt r WHERE r.id = :id")
    Optional<TaskAttempt> findById(@Param("id") long id);

    List<TaskAttempt> findByErrandBoyId(Long errandBoyId);

    List<TaskAttempt> findByTaskId(Long taskId);
}
