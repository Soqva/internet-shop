package com.s0qva.application.controller;

import com.s0qva.application.model.User;
import com.s0qva.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getOne(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> save(@RequestBody User user) {
        Long savedUserId = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUserId)
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok()
                .build();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
