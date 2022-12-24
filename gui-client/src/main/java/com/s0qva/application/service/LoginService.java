package com.s0qva.application.service;

import com.s0qva.application.dto.AuthDto;
import com.s0qva.application.dto.UserDto;
import com.s0qva.application.http.RestRequestSender;
import com.s0qva.application.session.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class LoginService {
    @Value("${rest-server.url.token}")
    private String tokenUrl;
    @Value("${rest-server.url.sign-in}")
    private String signInUrl;

    public boolean signIn(AuthDto authDto) {
        var headers = new HttpHeaders();

        headers.setBasicAuth(authDto.getUsername(), authDto.getPassword());

        var token = RestRequestSender.post(tokenUrl, new HttpEntity<>(null, headers), String.class).getBody();

        headers.clear();
        headers.setBearerAuth(token);

        var user = RestRequestSender.get(signInUrl, new HttpEntity<>(null, headers), UserDto.class).getBody();

        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        UserSession.getInstance().createUserSession(token, user);
        return true;
    }
}
