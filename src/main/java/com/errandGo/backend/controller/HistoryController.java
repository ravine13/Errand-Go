package com.errandGo.backend.controller;

import com.errandGo.backend.entities.History;
import com.errandGo.backend.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    // GET all history records
    @GetMapping
    public ResponseEntity<List<History>> getAllHistory() {
        return ResponseEntity.ok(historyService.getAllHistory());
    }

    // GET a single history record by ID
    @GetMapping("/{id}")
    public ResponseEntity<History> getHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(historyService.getHistoryById(id));
    }

    // POST: Log a new action for an account
    @PostMapping
    public ResponseEntity<History> logAction(
            @RequestParam Long accountId,
            @RequestParam String action
    ) {
        return ResponseEntity.ok(historyService.logAction(accountId, action));
    }

    // PUT: Update an existing history record's action
    @PutMapping("/{id}")
    public ResponseEntity<History> updateHistory(
            @PathVariable Long id,
            @RequestParam String newAction
    ) {
        return ResponseEntity.ok(historyService.updateHistory(id, newAction));
    }

    // DELETE a history record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        historyService.deleteHistory(id);
        return ResponseEntity.noContent().build();
    }
}
