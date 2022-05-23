package com.adminapp.services.impl;

import com.adminapp.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserService userService;

    public UserAuthService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        user = userService.getUser(username).get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        String type = user.getRole().toString();
        authorities.add(new SimpleGrantedAuthority(type));

        return new org.springframework.security.core.userdetails.User
                (username, user.getPassword(), authorities);
    }}
