package com.errandGo.backend.service;

import com.errandGo.backend.entities.DeviceToken;
import com.errandGo.backend.repositories.DeviceTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;

    // ✅ Save or update a device token
    @Transactional
    public DeviceToken saveDeviceToken(DeviceToken deviceToken) {
        return deviceTokenRepository.save(deviceToken);
    }

    // ✅ Get all device tokens
    public List<DeviceToken> getAllDeviceTokens() {
        return deviceTokenRepository.findAll();
    }

    // ✅ Get device token by token string
    public Optional<DeviceToken> getDeviceTokenByToken(String token) {
        return deviceTokenRepository.findByToken(token);
    }

    // ✅ Get device token by account ID
    public Optional<DeviceToken> getDeviceTokenByAccountId(Long accountId) {
        return deviceTokenRepository.findByAccountId(accountId);
    }

    // ✅ Get device token by ID
    public Optional<DeviceToken> getDeviceTokenById(Long id) {
        return deviceTokenRepository.findById(id);
    }

    // ✅ Delete device token by ID
    public void deleteDeviceToken(Long id) {
        if (!deviceTokenRepository.existsById(id)) {
            throw new IllegalArgumentException("Device token not found");
        }
        deviceTokenRepository.deleteById(id);
    }
}
