package com.errandGo.backend.controller;

import com.errandGo.backend.entities.ErrandBoy;
import com.errandGo.backend.service.ErrandBoyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/errand-boys")
@RequiredArgsConstructor
public class ErrandBoyController {

    private final ErrandBoyService errandBoyService;

    // GET all errand boys
    @GetMapping
    public ResponseEntity<List<ErrandBoy>> getAllErrandBoys() {
        return ResponseEntity.ok(errandBoyService.getAllErrandBoys());
    }

    // GET errand boy by ID
    @GetMapping("/{id}")
    public ResponseEntity<ErrandBoy> getErrandBoyById(@PathVariable Long id) {
        return ResponseEntity.ok(errandBoyService.getErrandBoyById(id));
    }

    // POST create new errand boy
    @PostMapping
    public ResponseEntity<ErrandBoy> createErrandBoy(@RequestBody ErrandBoy errandBoy) {
        return ResponseEntity.ok(errandBoyService.createErrandBoy(errandBoy));
    }

    // PUT update errand boy
    @PutMapping("/{id}")
    public ResponseEntity<ErrandBoy> updateErrandBoy(@PathVariable Long id, @RequestBody ErrandBoy updated) {
        return ResponseEntity.ok(errandBoyService.updateErrandBoy(id, updated));
    }

    // DELETE errand boy
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteErrandBoy(@PathVariable Long id) {
        errandBoyService.deleteErrandBoy(id);
        return ResponseEntity.noContent().build();
    }
}

