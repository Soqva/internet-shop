package com.s0qva.application.service;

import com.s0qva.application.dto.UserDto;
import com.s0qva.application.http.RestRequestSender;
import com.s0qva.application.session.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Value("${rest-server.url.users}")
    private String usersUrl;

    public List<UserDto> getAll() {
        var bearerToken = UserSession.getInstance().getBearerHttpHeader();
        var responseEntity = RestRequestSender.getAll(usersUrl, new HttpEntity<>(null, bearerToken), UserDto[].class);

        if (responseEntity.getBody() != null) {
            return Arrays.asList(responseEntity.getBody());
        }
        return Collections.emptyList();
    }

    public UserDto changeUserAccess(Long id, UserDto userDto) {
        var url = usersUrl + "/" + id;
        var bearerToken = UserSession.getInstance().getBearerHttpHeader();
        var responseEntity = RestRequestSender.update(url, new HttpEntity<>(userDto, bearerToken), UserDto.class);

        if (responseEntity.getBody() != null) {
            return responseEntity.getBody();
        }
        return new UserDto();
    }
}
