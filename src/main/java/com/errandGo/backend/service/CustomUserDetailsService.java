package com.errandGo.backend.service;

import com.errandGo.backend.entities.Account;
import com.errandGo.backend.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Authenticating user by email: {}", email);

        Optional<Account> accountOptional = accountRepository.findByEmail(email);

        if (accountOptional.isEmpty()) {
            log.warn("Account not found for email: {}", email);
            throw new UsernameNotFoundException("Email not found");
        }

        Account account = accountOptional.get();

        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole().name()))
        );
    }
}
