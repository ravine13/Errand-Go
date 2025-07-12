package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.Task;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT r FROM Task r WHERE r.id = :id")
    Optional<Task> findById(@Param("id") long id);

    List<Task> findByUserId(Long userId);

    List<Task> findByErrandBoyId(Long errandBoyId);

    @Query("SELECT SUM(t.amount) FROM Task t WHERE t.status = com.errandGo.backend.entities.Task.Status.COMPLETED")
    BigDecimal getTotalAmountCollected();


    @Query("SELECT FUNCTION('DATE_FORMAT', t.createdAt, '%Y-%m-%d') as day, COUNT(t) as total " +
            "FROM Task t GROUP BY day ORDER BY total DESC")
    List<Object[]> getTasksGroupedByDay();
}
