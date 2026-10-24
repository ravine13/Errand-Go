package com.errandGo.backend.config;

import com.errandGo.backend.entities.Account;
import com.errandGo.backend.repositories.AccountRepository;
import model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}") private String username;
    @Value("${app.admin.email:}")         private String email;
    @Value("${app.admin.password:}")      private String password;

    public AdminSeeder(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (accountRepository.existsByRole(Role.ADMIN)) return;
        if (email.isBlank() || password.length() < 8) {
            log.warn("No admin account exists. Set ADMIN_EMAIL and ADMIN_PASSWORD (min 8 chars) to create one.");
            return;
        }
        Account admin = new Account();
        admin.setUsername(username);
        admin.setEmail(email.trim().toLowerCase());
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(Role.ADMIN);
        accountRepository.save(admin);
        log.info("Initial admin account '{}' created.", username);
    }
}