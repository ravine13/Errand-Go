package com.errandGo.backend.service;



import java.util.ArrayList;
import java.util.Optional;


import com.errandGo.backend.entities.User;
import com.errandGo.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("GETTING USER PASSED ########" + username);


        Optional<User> userOptional = userRepository.findByUsername(username);

        // Check if the user is present
        if (userOptional.isEmpty()) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }

        User user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}

