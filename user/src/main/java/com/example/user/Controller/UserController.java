package com.example.user.Controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.user.Service.UserService;
import com.example.user.Model.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping ("/{userId}")
    public User findById(@PathVariable("userId") Long userId) {
        return userService.findById(userId);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest request) {
        User created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping ("/{userId}")
    public User updateUser (
        @PathVariable("userId") Long userId,
        @Valid @RequestBody UserRequest request
    ) {
        return userService.update(userId, request);
    }

    @DeleteMapping ("/{userId}")
    public ResponseEntity<Void> deleteUser (@PathVariable("userId") Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}