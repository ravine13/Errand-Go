package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT r FROM Task r WHERE r.id = :id")
    Optional<Task> findById(@Param("id") long id);

    List<Task> findByUserId(Long userId);

    List<Task> findByErrandBoyId(Long errandBoyId);

}
