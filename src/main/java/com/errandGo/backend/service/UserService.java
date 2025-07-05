package com.errandGo.backend.service;

import com.errandGo.backend.entities.User; // Or Account, depending on what you called it
import com.errandGo.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByAccount_Username(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByAccount_Email(email);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByAccount_Username(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByAccount_Email(email);
    }

    public User createUser(User user) {
        if (usernameExists(user.getAccount().getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (emailExists(user.getAccount().getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        return userRepository.save(user);
    }
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);

        // Update fields in Account (which now contains all shared info)
        if (existingUser.getAccount() != null && updatedUser.getAccount() != null) {
            existingUser.getAccount().setUsername(updatedUser.getAccount().getUsername());
            existingUser.getAccount().setEmail(updatedUser.getAccount().getEmail());
            existingUser.getAccount().setLocation(updatedUser.getAccount().getLocation());
            existingUser.getAccount().setRole(updatedUser.getAccount().getRole());
            existingUser.getAccount().setDeviceToken(updatedUser.getAccount().getDeviceToken());
            existingUser.getAccount().setPhoneNumber(updatedUser.getAccount().getPhoneNumber());
            existingUser.getAccount().setProfilePicture(updatedUser.getAccount().getProfilePicture());
            existingUser.getAccount().setLatitude(updatedUser.getAccount().getLatitude());
            existingUser.getAccount().setLongitude(updatedUser.getAccount().getLongitude());
        }

        return userRepository.save(existingUser);
    }
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
