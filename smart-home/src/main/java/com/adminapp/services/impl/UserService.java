package com.adminapp.services.impl;

import com.adminapp.domain.User;
import com.adminapp.domain.enums.Role;
import com.adminapp.repository.UserRepository;
import com.adminapp.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User changeRole(User user, Integer role) {
        User user1 = userRepository.findByUsername(user.getUsername()).orElse(null);
        user1.setRole(Role.getRoleFromInt(role));
        return userRepository.save(user1);
    }

    @Override
    public Boolean deleteUser(String username) {
        User user1 = userRepository.findByUsername(username).orElse(null);
        if(user1 !=null) {
            userRepository.delete(user1);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
