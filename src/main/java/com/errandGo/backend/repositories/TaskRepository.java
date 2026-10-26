package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.Account;
import com.errandGo.backend.entities.Task;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);
    List<Task> findByErrandBoyId(Long errandBoyId);

    @Query("SELECT SUM(t.amount) FROM Task t WHERE t.status = com.errandGo.backend.entities.Task.Status.COMPLETED")
    BigDecimal getTotalAmountCollected();

    @Query("SELECT FUNCTION('DATE_FORMAT', t.createdAt, '%Y-%m-%d') as day, COUNT(t) " +
            "FROM Task t WHERE t.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY day ORDER BY COUNT(t) DESC")
    List<Object[]> getTasksGroupedByDayBetween(@Param("startDate") LocalDateTime start,
                                               @Param("endDate") LocalDateTime end);
    // TaskRepository
    List<Task> findByUserOrderByCreatedAtDesc(Account user);
    List<Task> findByErrandBoyOrderByCreatedAtDesc(Account errandBoy);

    public interface NearbyTaskRow {
        Long getId(); String getTitle(); BigDecimal getAmount();
        Double getLatitude(); Double getLongitude(); Double getDistanceKm();
    }

    @Query(value = """
    SELECT t.id AS id, t.title AS title, t.amount AS amount,
           t.latitude AS latitude, t.longitude AS longitude,
           (6371 * ACOS(LEAST(1, COS(RADIANS(:lat)) * COS(RADIANS(t.latitude))
               * COS(RADIANS(t.longitude) - RADIANS(:lng))
               + SIN(RADIANS(:lat)) * SIN(RADIANS(t.latitude))))) AS distanceKm
    FROM task t
    WHERE t.status = 'PENDING' AND t.latitude IS NOT NULL
      AND t.latitude BETWEEN :minLat AND :maxLat
      AND t.longitude BETWEEN :minLng AND :maxLng
    HAVING distanceKm <= :radiusKm
    ORDER BY distanceKm LIMIT 50
    """, nativeQuery = true)
    List<NearbyTaskRow> findNearby(@Param("lat") double lat, @Param("lng") double lng,
                                   @Param("minLat") double minLat, @Param("maxLat") double maxLat,
                                   @Param("minLng") double minLng, @Param("maxLng") double maxLng,
                                   @Param("radiusKm") double radiusKm);

    // Atomic transitions: each returns 0 if the task isn't in the expected state
    @Modifying @Query("UPDATE Task t SET t.status = com.errandGo.backend.entities.Task.Status.ACCEPTED, " +
            "t.errandBoy = :boy, t.acceptedAt = :now WHERE t.id = :id " +
            "AND t.status = com.errandGo.backend.entities.Task.Status.PENDING")
    int claim(@Param("id") Long id, @Param("boy") Account boy, @Param("now") LocalDateTime now);

    @Modifying @Query("UPDATE Task t SET t.status = com.errandGo.backend.entities.Task.Status.IN_PROGRESS, " +
            "t.startedAt = :now WHERE t.id = :id AND t.errandBoy = :boy " +
            "AND t.status = com.errandGo.backend.entities.Task.Status.ACCEPTED")
    int start(@Param("id") Long id, @Param("boy") Account boy, @Param("now") LocalDateTime now);

    @Modifying @Query("UPDATE Task t SET t.status = com.errandGo.backend.entities.Task.Status.COMPLETED, " +
            "t.completedAt = :now WHERE t.id = :id AND t.errandBoy = :boy " +
            "AND t.status = com.errandGo.backend.entities.Task.Status.IN_PROGRESS")
    int complete(@Param("id") Long id, @Param("boy") Account boy, @Param("now") LocalDateTime now);

    @Modifying @Query("UPDATE Task t SET t.status = com.errandGo.backend.entities.Task.Status.CANCELLED " +
            "WHERE t.id = :id AND t.user = :user " +
            "AND t.status = com.errandGo.backend.entities.Task.Status.PENDING")
    int cancel(@Param("id") Long id, @Param("user") Account user);

}
