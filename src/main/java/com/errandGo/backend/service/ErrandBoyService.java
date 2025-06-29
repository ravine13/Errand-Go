package com.errandGo.backend.service;

import com.errandGo.backend.entities.ErrandBoy;
import com.errandGo.backend.repositories.ErrandBoyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrandBoyService {

    private final ErrandBoyRepository errandBoyRepository;
    private final PasswordEncoder passwordEncoder;

    public List<ErrandBoy> getAllErrandBoys() {
        return errandBoyRepository.findAll();
    }

    public ErrandBoy getErrandBoyById(Long id) {
        return errandBoyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Errand boy not found"));
    }

    @Transactional
    public ErrandBoy createErrandBoy(ErrandBoy errandBoy) {
        if (errandBoy.getEmail() == null || errandBoy.getEmail().isBlank()) {
            String baseEmail = errandBoy.getFullName()
                    .toLowerCase()
                    .replaceAll("\\s+", "") + "@errands.com";

            String email = baseEmail;
            int count = 1;

            while (errandBoyRepository.existsByEmail(email)) {
                email = errandBoy.getFullName()
                        .toLowerCase()
                        .replaceAll("\\s+", "") + count + "@errands.com";
                count++;
            }

            errandBoy.setEmail(email);
        }

        errandBoy.setPassword(passwordEncoder.encode(errandBoy.getPassword()));
        return errandBoyRepository.save(errandBoy);
    }

    @Transactional
    public ErrandBoy updateErrandBoy(Long id, ErrandBoy updated) {
        ErrandBoy existing = getErrandBoyById(id);
        existing.setIsOnline(updated.getIsOnline());
        existing.setAverageRating(updated.getAverageRating());
        existing.setTotalCompletedTasks(updated.getTotalCompletedTasks());

        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updated.getPassword()));
        }

        return errandBoyRepository.save(existing);
    }

    // ✅ Only ADMINs can delete
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteErrandBoy(Long id) {
        if (!errandBoyRepository.existsById(id)) {
            throw new IllegalArgumentException("Errand boy not found");
        }
        errandBoyRepository.deleteById(id);
    }
}
