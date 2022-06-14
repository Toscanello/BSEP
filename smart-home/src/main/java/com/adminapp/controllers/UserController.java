package com.adminapp.controllers;

import com.adminapp.domain.User;
import com.adminapp.domain.enums.Role;
import com.adminapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "users")
public class UserController {
    @Autowired
    IUserService userService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/addNewUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addNewUser(@RequestBody User user){
        return new ResponseEntity<>(userService.addNewUser(user), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "/changeRole/{role}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> changeRole(@RequestBody User user,@PathVariable Integer role){
        return new ResponseEntity<>(userService.changeRole(user, role),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/delete/{username}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String username){
        return new ResponseEntity<>(userService.deleteUser(username),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/getAll")
    public ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }

    @GetMapping(path = "/search/{query}")
    public ResponseEntity<List<User>> searchUsers(@PathVariable String query){
        return new ResponseEntity<>(userService.searchUsers(query),HttpStatus.OK);
    }
}
