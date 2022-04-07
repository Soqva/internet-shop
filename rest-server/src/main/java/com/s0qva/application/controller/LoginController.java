package com.s0qva.application.controller;

import com.s0qva.application.dto.user.UserAuthenticationDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserReadingDto> signIn(@Valid @RequestBody UserAuthenticationDto userAuthenticationDto) {
        log.info("Received a request for sign in. UserAuthenticationDto: {}", userAuthenticationDto);
        UserReadingDto user = loginService.signIn(userAuthenticationDto);

        log.info("Sending a user: {}. The response status is OK", user);
        return ResponseEntity.ok(user);
    }
}
