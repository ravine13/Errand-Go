package com.errandGo.backend.controller;

import com.errandGo.backend.dto.ProfileDTO;
import com.errandGo.backend.entities.Profile;
import com.errandGo.backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{accountId}")
    public ResponseEntity<Profile> createOrUpdateProfile(
            @PathVariable Long accountId,
            @RequestBody ProfileDTO profileDTO
    ) {
        Profile profile = profileService.createOrUpdateProfile(accountId, profileDTO.toEntity());
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long accountId) {
        Profile profile = profileService.getProfileByAccountId(accountId);
        return ResponseEntity.ok(profile);
    }
}
