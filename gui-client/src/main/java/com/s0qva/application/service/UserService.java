package com.s0qva.application.service;

import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.http.RestRequestSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Value("${rest-server.url.users}")
    private String usersUrl;

    public List<UserReadingDto> getAllUsers() {
        ResponseEntity<UserReadingDto[]> responseEntity = RestRequestSender.getAll(usersUrl, UserReadingDto[].class);

        if (responseEntity.getBody() != null) {
            return Arrays.asList(responseEntity.getBody());
        }

        return Collections.emptyList();
    }
}
