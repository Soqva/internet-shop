package com.s0qva.application.service;

import com.s0qva.application.dto.UserDto;
import com.s0qva.application.http.RestRequestSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.CREATED;

@Service
@Slf4j
public class RegistrationService {
    @Value("${rest-server.url.sign-up}")
    private String usersUrl;

    public boolean signUp(UserDto userDto) {
        return RestRequestSender.post(usersUrl, new HttpEntity<>(userDto), UserDto.class)
                .getStatusCode()
                .equals(CREATED);
    }
}
