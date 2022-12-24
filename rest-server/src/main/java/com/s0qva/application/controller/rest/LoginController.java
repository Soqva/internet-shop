package com.s0qva.application.controller.rest;

import com.s0qva.application.dto.UserDto;
import com.s0qva.application.service.LoginService;
import com.s0qva.application.service.UserService;
import com.s0qva.application.util.ServletUriUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static java.util.stream.Collectors.joining;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final UserService userService;
    private final JwtEncoder encoder;

    @PostMapping("/token")
    @SecurityRequirement(name = "token-access")
    public String getToken(Authentication authentication) {
        var claims = getJwtClaimsSet(authentication);

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @GetMapping("/sign-in")
    @SecurityRequirement(name = "api-access")
    public ResponseEntity<UserDto> signIn(Authentication authentication) {
        var userDto = userService.getByUsername(authentication.getName());

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody UserDto userDto) {
        var registeredUserId = loginService.signUp(userDto);
        var location = ServletUriUtil.getUri("/api/v1/users/{id}", registeredUserId);

        return ResponseEntity.created(location).build();
    }

    private JwtClaimsSet getJwtClaimsSet(Authentication authentication) {
        var startDate = Instant.now();
        var expiryTimeSeconds = 36000L;
        var scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(joining(" "));
        return JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(startDate)
                .expiresAt(startDate.plusSeconds(expiryTimeSeconds))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
    }
}
