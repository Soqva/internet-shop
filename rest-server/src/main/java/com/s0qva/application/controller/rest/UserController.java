package com.s0qva.application.controller.rest;

import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.dto.UserDto;
import com.s0qva.application.service.OrderService;
import com.s0qva.application.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "api-access")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(@AuthenticationPrincipal Jwt jwt) {
        var users = userService.getAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable(name = "id") Long id) {
        var user = userService.getById(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable(name = "id") Long userId) {
        var orders = orderService.getAllByUserId(userId);

        return ResponseEntity.ok(orders);
    }
}
