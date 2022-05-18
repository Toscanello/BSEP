package com.adminapp.services.impl;

import com.adminapp.domain.User;
import com.adminapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

}
