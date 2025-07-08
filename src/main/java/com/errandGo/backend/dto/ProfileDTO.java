package com.errandGo.backend.dto;

import com.errandGo.backend.entities.Profile;
import lombok.Data;

@Data
public class ProfileDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureUrl;

    public Profile toEntity() {
        return Profile.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .profilePictureUrl(profilePictureUrl)
                .build();
    }
}
