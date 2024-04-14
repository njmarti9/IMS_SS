package com.skillstorm.inventory_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.inventory_management.models.User;
import com.skillstorm.inventory_management.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://127.0.0.1:5501/")
public class UserController {
    
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable int id) {
        User user = userService.findUserById(id);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<User>> findUsersByName(@PathVariable String name) {
        List<User> users = userService.findUsersByName(name);

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newuser = userService.saveUser(user);

        return new ResponseEntity<User>(newuser, HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User newuser = userService.saveUser(user);
        return new ResponseEntity<User>(newuser, HttpStatus.OK);
    }

    @PutMapping("/user/updateUsername")
    public ResponseEntity<Integer> updateUsername(@RequestBody User user, @RequestParam String newName) {
        int updated = userService.updateUsername(user, newName);
        return new ResponseEntity<Integer>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }
}
