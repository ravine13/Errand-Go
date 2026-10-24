package com.errandGo.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    public enum AccountType { CLIENT, RUNNER }

    @NotBlank @Size(min = 3, max = 64)
    @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "letters, digits, dots, dashes and underscores only")
    private String username;

    @NotBlank @Email @Size(max = 120)
    private String email;

    @NotBlank @Size(min = 8, max = 72)
    private String password;

    @Size(max = 20)  private String phoneNumber;
    @Size(max = 120) private String location;

    /** Optional. Defaults to CLIENT. Any other value is rejected automatically (400). */
    private AccountType accountType;
}