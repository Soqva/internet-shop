package com.s0qva.application.controller;

import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.dto.user.UserIdDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserReadingDto>> getAll() {
        List<UserReadingDto> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserReadingDto> getOne(@PathVariable Long id) {
        UserReadingDto user = userService.getUser(id);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> save(@RequestBody UserCreationDto user) {
        UserIdDto savedUser = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserReadingDto> update(@PathVariable Long id, @RequestBody UserCreationDto user) {
        UserReadingDto updatedUser = userService.updateUser(id, user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok()
                .build();
    }
}
