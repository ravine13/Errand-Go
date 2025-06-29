package com.errandGo.backend.service;

import com.errandGo.backend.entities.RevokedToken;
import com.errandGo.backend.repositories.RevokedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RevokedTokenService {

    private final RevokedTokenRepository revokedTokenRepository;

    public RevokedToken revokeToken(String token) {
        RevokedToken revokedToken = RevokedToken.builder()
                .token(token)
                .revokedAt(LocalDateTime.now())
                .build();

        return revokedTokenRepository.save(revokedToken);
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokenRepository.existsByToken(token);
    }

    public Optional<RevokedToken> findByToken(String token) {
        return revokedTokenRepository.findByToken(token);
    }
}
