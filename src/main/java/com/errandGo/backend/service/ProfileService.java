package com.errandGo.backend.service;

import com.errandGo.backend.entities.Account;
import com.errandGo.backend.entities.Profile;
import com.errandGo.backend.repositories.AccountRepository;
import com.errandGo.backend.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AccountRepository accountRepository;

    public Profile createOrUpdateProfile(Long accountId, Profile profileDetails) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Optional<Profile> existing = profileRepository.findByAccountId(accountId);

        Profile profile = existing.orElse(new Profile());
        profile.setAccount(account);
        profile.setFirstName(profileDetails.getFirstName());
        profile.setLastName(profileDetails.getLastName());
        profile.setPhoneNumber(profileDetails.getPhoneNumber());
        profile.setProfilePictureUrl(profileDetails.getProfilePictureUrl());

        return profileRepository.save(profile);
    }

    public Profile getProfileByAccountId(Long accountId) {
        return profileRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }
}
