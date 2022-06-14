package com.adminapp.services.impl;

import com.adminapp.domain.User;
import com.adminapp.domain.enums.Role;
import com.adminapp.repository.UserRepository;
import com.adminapp.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if(user1 != null) {
            user1.setRole(Role.getRoleFromInt(role));
            userRepository.save(user1);
        }
        return user1;
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

    @Override
    public List<User> searchUsers(String query) {
        List<User> users = userRepository.findAll();
        String phrase = query.toLowerCase();
        return users.stream()
                .filter(u->u.getUsername().toLowerCase().contains(phrase)
                        || u.getName().toLowerCase().contains(phrase)
                        || u.getSurname().toLowerCase().contains(phrase)
                ).collect(Collectors.toList());
    }
}
