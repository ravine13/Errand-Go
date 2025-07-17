package com.errandGo.backend.controller;

import com.errandGo.backend.entities.Account;
import com.errandGo.backend.entities.ErrandBoy;
import com.errandGo.backend.entities.User;
import com.errandGo.backend.repositories.AccountRepository;
import com.errandGo.backend.repositories.ErrandBoyRepository;
import com.errandGo.backend.repositories.UserRepository;
import com.errandGo.backend.security.JwtUtil;
import com.errandGo.backend.service.CustomUserDetailsService;
import model.AuthenticationRequest;
import model.AuthenticationResponse;
import model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ErrandBoyRepository errandBoyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Account account = accountRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Account not found by email"));

        String jwt = jwtUtil.generateToken(account);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, account.getRole().name()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount.isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }

        String email = account.getEmail();
        Role role;
        if (email.endsWith("@errandboy.com")) {
            role = Role.ERRAND_BOY;
        } else if (email.endsWith("@gmail.com")) {
            role = Role.USER;
        } else {
            role = Role.ADMIN;
        }

        account.setRole(role);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account savedAccount = accountRepository.save(account);

        switch (role) {
            case USER -> {
                User newUser = new User();
                newUser.setAccount(savedAccount);
                userRepository.save(newUser);
            }
            case ERRAND_BOY -> {
                ErrandBoy errandBoy = new ErrandBoy();
                errandBoy.setAccount(savedAccount);
                errandBoy.setFullName(savedAccount.getUsername());
                errandBoyRepository.save(errandBoy);
            }
//            case ADMIN -> {
//
          //  }
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Account registered successfully as " + role.name());
    }
}
