package com.errandGo.backend.service;

import com.errandGo.backend.dto.CreateTaskRequest;
import com.errandGo.backend.dto.NearbyTaskView;
import com.errandGo.backend.dto.TaskView;
import com.errandGo.backend.entities.Account;
import com.errandGo.backend.entities.Category;
import com.errandGo.backend.entities.Task;
import com.errandGo.backend.repositories.AccountRepository;
import com.errandGo.backend.repositories.CategoryRepository;
import com.errandGo.backend.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskFlowService {

    private final TaskRepository tasks;
    private final AccountRepository accounts;
    private final CategoryRepository categories;

    @Transactional
    public TaskView create(String username, CreateTaskRequest r) {
        Account user = accounts.findByUsername(username).orElseThrow();
        Category cat = categories.findById(r.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown category"));
        Task t = Task.builder()
                .title(r.title().trim())
                .description(r.description())
                .location(r.location())
                .latitude(r.latitude())
                .longitude(r.longitude())
                .amount(r.amount())
                .category(cat)
                .user(user)
                .status(Task.Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        return TaskView.from(tasks.save(t));
    }

    @Transactional(readOnly = true)
    public List<TaskView> mine(String username) {
        Account a = accounts.findByUsername(username).orElseThrow();
        return tasks.findByUserOrderByCreatedAtDesc(a).stream().map(TaskView::from).toList();
    }

    @Transactional(readOnly = true)
    public List<TaskView> runnerTasks(String username) {
        Account a = accounts.findByUsername(username).orElseThrow();
        return tasks.findByErrandBoyOrderByCreatedAtDesc(a).stream().map(TaskView::from).toList();
    }

    @Transactional(readOnly = true)
    public List<NearbyTaskView> nearby(double lat, double lng, double radiusKm) {
        if (lat < -90 || lat > 90 || lng < -180 || lng > 180) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid coordinates");
        }
        radiusKm = Math.min(Math.max(radiusKm, 0.5), 25);
        double dLat = radiusKm / 111.0;
        double dLng = radiusKm / (111.0 * Math.max(0.01, Math.cos(Math.toRadians(lat))));
        return tasks.findNearby(lat, lng, lat - dLat, lat + dLat, lng - dLng, lng + dLng, radiusKm).stream()
                .map(r -> new NearbyTaskView(r.getId(), r.getTitle(), r.getAmount(),
                        Math.round(r.getDistanceKm() * 10) / 10.0,
                        Math.round(r.getLatitude() * 100) / 100.0,   // ~1 km fuzz, never the exact pin
                        Math.round(r.getLongitude() * 100) / 100.0))
                .toList();
    }

    @Transactional
    public void accept(Long id, String username) {
        Account boy = accounts.findByUsername(username).orElseThrow();
        check(tasks.claim(id, boy, LocalDateTime.now()), "Task is no longer available");
    }

    @Transactional
    public void start(Long id, String username) {
        Account boy = accounts.findByUsername(username).orElseThrow();
        check(tasks.start(id, boy, LocalDateTime.now()), "Task can't be started");
    }

    @Transactional
    public void complete(Long id, String username) {
        Account boy = accounts.findByUsername(username).orElseThrow();
        check(tasks.complete(id, boy, LocalDateTime.now()), "Task can't be completed");
    }

    @Transactional
    public void cancel(Long id, String username) {
        Account user = accounts.findByUsername(username).orElseThrow();
        check(tasks.cancel(id, user), "Only pending tasks can be cancelled");
    }

    private void check(int updated, String message) {
        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, message);
        }
    }
}