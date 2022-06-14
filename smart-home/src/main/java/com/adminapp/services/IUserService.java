package com.adminapp.services;

import com.adminapp.domain.User;

import java.util.List;

public interface IUserService {

    User addNewUser(User user);
    User changeRole(User user, Integer role);
    Boolean deleteUser(String username);
    List<User> getAll();

    List<User> searchUsers(String query);
}
