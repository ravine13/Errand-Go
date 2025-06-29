package com.errandGo.backend.service;

import com.errandGo.backend.entities.Admin;
import com.errandGo.backend.repositories.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + id));
    }

    @Transactional
    public Admin createAdmin(Admin admin) {
        if (admin.getEmail() == null || admin.getEmail().isBlank()) {
            String baseEmail = admin.getFullName()
                    .toLowerCase()
                    .replaceAll("\\s+", "") + "@admin.com";

            String email = baseEmail;
            int count = 1;

            while (adminRepository.existsByEmail(email)) {
                email = admin.getFullName()
                        .toLowerCase()
                        .replaceAll("\\s+", "") + count + "@admin.com";
                count++;
            }

            admin.setEmail(email);
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        return adminRepository.save(admin);
    }

    @Transactional
    public Admin updateAdmin(Long id, Admin updated) {
        Admin existing = getAdminById(id);

        existing.setFullName(updated.getFullName());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setPassword(updated.getPassword());

        return adminRepository.save(existing);
    }

    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new IllegalArgumentException("Admin not found");
        }
        adminRepository.deleteById(id);
    }
}
