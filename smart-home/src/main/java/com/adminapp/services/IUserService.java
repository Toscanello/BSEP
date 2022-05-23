package com.adminapp.services;

import com.adminapp.domain.User;

import java.util.List;

public interface IUserService {

    public User addNewUser(User user);
    public User changeRole(User user, Integer role);
    public Boolean deleteUser(String username);
    public List<User> getAll();
}
