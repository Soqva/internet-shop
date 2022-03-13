package com.s0qva.application.service;

import com.s0qva.application.dto.user.UserAuthenticationDto;
import com.s0qva.application.http.RestRequestSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Value("${rest-server.url.users.sign-in}")
    private String usersSignInUrl;

    public boolean signIn(UserAuthenticationDto userCreationDto) {
        ResponseEntity<Void> responseEntity = RestRequestSender.signIn(usersSignInUrl, userCreationDto);

        return responseEntity.getStatusCode()
                .equals(HttpStatus.OK);
    }
}
