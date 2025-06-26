package com.errandGo.backend.service;

import com.errandGo.backend.entities.ErrandBoy;
import com.errandGo.backend.repositories.ErrandBoyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrandBoyService {

    private final ErrandBoyRepository errandBoyRepository;

    // ✅ Get all errand boys
    public List<ErrandBoy> getAllErrandBoys() {
        return errandBoyRepository.findAll();
    }

    // ✅ Get one errand boy by ID
    public ErrandBoy getErrandBoyById(Long id) {
        return errandBoyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Errand boy not found"));
    }

    // ✅ Create a new errand boy
    public ErrandBoy createErrandBoy(ErrandBoy errandBoy) {
        return errandBoyRepository.save(errandBoy);
    }

    // ✅ Update an errand boy
    @Transactional
    public ErrandBoy updateErrandBoy(Long id, ErrandBoy updated) {
        ErrandBoy existing = getErrandBoyById(id);
        existing.setIsOnline(updated.getIsOnline());
        existing.setAverageRating(updated.getAverageRating());
        existing.setTotalCompletedTasks(updated.getTotalCompletedTasks());
        return errandBoyRepository.save(existing);
    }

    // ✅ Delete an errand boy
    public void deleteErrandBoy(Long id) {
        if (!errandBoyRepository.existsById(id)) {
            throw new IllegalArgumentException("Errand boy not found");
        }
        errandBoyRepository.deleteById(id);
    }
}
