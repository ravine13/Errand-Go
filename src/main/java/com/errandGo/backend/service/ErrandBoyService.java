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

//        System.out.println("Incoming password: " + errandBoy.getPassword());
        if (errandBoy.getAccount().getPassword() == null || errandBoy.getAccount().getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        if (errandBoy.getEmail() == null || errandBoy.getEmail().isBlank()) {
            String baseEmail = errandBoy.getFullName()
                    .toLowerCase()
                    .replaceAll("\\s+", "") + "@errandboy.com";

            String email = baseEmail;
            int count = 1;

            // ensure uniqueness
            while (errandBoyRepository.existsByAccount_Email(email)) {
                email = errandBoy.getFullName()
                        .toLowerCase()
                        .replaceAll("\\s+", "") + count + "@errandboy.com";
                count++;
            }

            errandBoy.getAccount().setEmail(email);
        }

        errandBoy.getAccount().setPassword(passwordEncoder.encode(errandBoy.getAccount().getPassword()));
        return errandBoyRepository.save(errandBoy);
    }


    @Transactional
    public ErrandBoy updateErrandBoy(Long id, ErrandBoy updated) {
        ErrandBoy existing = getErrandBoyById(id);
        existing.setIsOnline(updated.getIsOnline());
        existing.setAverageRating(updated.getAverageRating());
        existing.setTotalCompletedTasks(updated.getTotalCompletedTasks());

        if (updated.getAccount().getPassword() != null && !updated.getAccount().getPassword().isBlank()) {
            existing.getAccount().setPassword(passwordEncoder.encode(updated.getAccount().getPassword()));
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
