package com.errandGo.backend.repositories;

import com.errandGo.backend.entities.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

    Optional<DeviceToken> findByToken(String token);
    Optional<DeviceToken> findByAccountId(Long accountId);
}
