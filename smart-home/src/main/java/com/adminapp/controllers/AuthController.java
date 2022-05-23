package com.adminapp.controllers;

import com.adminapp.dto.JwtAuthenticationRequest;
import com.adminapp.dto.UserTokenState;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import com.adminapp.utils.TokenUtils;

import java.util.Collection;

@RestController
@RequestMapping(value = "auth")
public class AuthController {

    private final TokenUtils tokenUtils;

    private final AuthenticationManager authenticationManager;

    public AuthController(TokenUtils tokenUtils, AuthenticationManager authenticationManager) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
    }

//    @ExceptionHandler(value = NotFoundException.class)
//    public ResponseEntity handleNullEmployeeException(NotFoundException notFoundException) {
//        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.FORBIDDEN);
//    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        String jwt = tokenUtils.generateToken(user.getUsername(), user.getAuthorities());
        return ResponseEntity.ok(new UserTokenState(jwt));
    }
}
