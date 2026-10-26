package com.errandGo.backend.controller;

import com.errandGo.backend.dto.NearbyTaskView;
import com.errandGo.backend.dto.TaskView;
import com.errandGo.backend.service.TaskFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/errand-boy/tasks")
@RequiredArgsConstructor
public class RunnerTaskController {

    private final TaskFlowService service;

    @GetMapping("/nearby")
    public List<NearbyTaskView> nearby(@RequestParam double lat,
                                       @RequestParam double lng,
                                       @RequestParam(defaultValue = "5") double radiusKm) {
        return service.nearby(lat, lng, radiusKm);
    }

    @GetMapping("/mine")
    public List<TaskView> mine(Authentication auth) {
        return service.runnerTasks(auth.getName());
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<Void> accept(@PathVariable Long id, Authentication auth) {
        service.accept(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> start(@PathVariable Long id, Authentication auth) {
        service.start(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable Long id, Authentication auth) {
        service.complete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}