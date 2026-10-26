package com.errandGo.backend.controller;

import com.errandGo.backend.dto.CreateTaskRequest;
import com.errandGo.backend.dto.TaskView;
import com.errandGo.backend.service.TaskFlowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/tasks")
@RequiredArgsConstructor
public class UserTaskController {

    private final TaskFlowService service;

    @PostMapping
    public ResponseEntity<TaskView> create(@Valid @RequestBody CreateTaskRequest request, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(auth.getName(), request));
    }

    @GetMapping
    public List<TaskView> mine(Authentication auth) {
        return service.mine(auth.getName());
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id, Authentication auth) {
        service.cancel(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}