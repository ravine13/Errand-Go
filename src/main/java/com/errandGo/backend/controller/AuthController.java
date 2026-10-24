package com.errandGo.backend.controller;

import com.errandGo.backend.dto.RegisterRequest;
import com.errandGo.backend.entities.Account;
import com.errandGo.backend.entities.ErrandBoy;
import com.errandGo.backend.entities.User;
import com.errandGo.backend.repositories.AccountRepository;
import com.errandGo.backend.repositories.ErrandBoyRepository;
import com.errandGo.backend.repositories.UserRepository;
import com.errandGo.backend.security.AuthCookies;
import com.errandGo.backend.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import model.AuthenticationRequest;
import model.Role;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    public static final String COOKIE_NAME = "errandgo_token";

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthCookies authCookies;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ErrandBoyRepository errandBoyRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest request,
                                   HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
        Account account = accountRepository.findByUsername(request.getUsername()).orElseThrow();
        response.addHeader(HttpHeaders.SET_COOKIE,
                authCookies.create(jwtUtil.generateToken(account)).toString());
        return ResponseEntity.ok(profile(account));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase();

        // Fast path for friendly messages; the DB unique constraints are the real guarantee
        if (accountRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        if (accountRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        Role role = request.getAccountType() == RegisterRequest.AccountType.RUNNER ? Role.ERRAND_BOY : Role.USER;

        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setPhoneNumber(request.getPhoneNumber());
        account.setLocation(request.getLocation());
        account.setRole(role);
        account = accountRepository.saveAndFlush(account); // surfaces constraint violations here

        if (role == Role.ERRAND_BOY) {
            ErrandBoy errandBoy = new ErrandBoy();
            errandBoy.setAccount(account);
            errandBoy.setFullName(account.getUsername());
            errandBoyRepository.save(errandBoy);
        } else {
            User user = new User();
            user.setAccount(account);
            userRepository.save(user);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Account registered successfully as " + role.name());
    }

    /** The frontend calls this on page load to learn who is logged in. */
    @GetMapping("/auth/me")
    public Map<String, Object> me(Authentication authentication) {
        return profile(accountRepository.findByUsername(authentication.getName()).orElseThrow());
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(Authentication authentication, HttpServletResponse response) {
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            accountRepository.findByUsername(authentication.getName()).ifPresent(a -> {
                a.setTokenValidAfter(Instant.now().truncatedTo(ChronoUnit.SECONDS));
                accountRepository.save(a);
            });
        }
        response.addHeader(HttpHeaders.SET_COOKIE, authCookies.clear().toString());
        return ResponseEntity.noContent().build();
    }

    private Map<String, Object> profile(Account a) {
        return Map.of("id", a.getId(), "username", a.getUsername(), "email", a.getEmail(), "role", a.getRole().name());
    }
}