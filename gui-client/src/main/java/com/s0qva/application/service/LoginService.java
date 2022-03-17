package com.s0qva.application.service;

import com.s0qva.application.dto.user.UserAuthenticationDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.http.RestRequestSender;
import com.s0qva.application.session.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Value("${rest-server.url.sign-in}")
    private String signInUrl;

    public boolean signIn(UserAuthenticationDto userAuthenticationDto) {
        ResponseEntity<UserReadingDto> responseEntity = RestRequestSender.signIn(signInUrl, userAuthenticationDto);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            UserReadingDto user = responseEntity.getBody();
            UserSession.getInstance().createUserSession(user);

            return true;
        }

        return false;
    }
}
