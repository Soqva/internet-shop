package com.s0qva.application.controller;

import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.dto.user.UserIdDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserReadingDto>> getAll() {
        log.info("Received a request for getting all users.");
        List<UserReadingDto> users = userService.getAllUsers();

        log.info("Sending a list of users: {}. The response status is OK", users);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserReadingDto> getOneById(@PathVariable Long id) {
        log.info("Received a request for getting a user by its id = {}.", id);
        UserReadingDto user = userService.getUserById(id);

        log.info("Sending a user: {}. The response status is OK", user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{id}/orders")
    public ResponseEntity<List<OrderReadingDto>> getAllOrdersForSpecificUser(@PathVariable Long id) {
        log.info("Received a request for getting all user's orders. User's id: {}", id);
        List<OrderReadingDto> userOrders = userService.getAllOrdersByUserId(id);

        log.info("Sending a list of user's orders: {}. The response status is OK", userOrders);
        return ResponseEntity.ok(userOrders);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> save(@Valid @RequestBody UserCreationDto user) {
        log.info("Received a request for saving a user. OrderCreationDto: {}.", user);
        UserIdDto savedUserId = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUserId.getId())
                .toUri();

        log.info("Nothing to send. UserIdDto savedUserId {}. URI location: {}. The response status is CREATED",
                savedUserId,
                location);
        return ResponseEntity.created(location)
                .build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserReadingDto> update(@PathVariable Long id, @RequestBody UserCreationDto user) {
        log.info("Received a request for updating a user. User's Id: {}. UserCreationDto: {}.", id, user);
        UserReadingDto updatedUser = userService.updateUser(id, user);

        log.info("Sending a user: {}. The response status is OK", updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Received a request for deleting a user. User's Id: {}.", id);
        userService.deleteUser(id);

        log.info("Nothing to send. The response status is OK");
        return ResponseEntity.ok()
                .build();
    }
}
