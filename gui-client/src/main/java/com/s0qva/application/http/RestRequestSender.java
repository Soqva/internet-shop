package com.s0qva.application.http;

import com.s0qva.application.dto.CreationDto;
import com.s0qva.application.dto.ReadingDto;
import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.dto.user.UserAuthenticationDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.http.error.handler.ServerResponseErrorHandler;
import lombok.experimental.UtilityClass;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@UtilityClass
public class RestRequestSender {
    private static final RestTemplate REST_TEMPLATE;

    static {
        REST_TEMPLATE = new RestTemplateBuilder()
                .errorHandler(new ServerResponseErrorHandler())
                .build();
    }

    public ResponseEntity<UserReadingDto> signIn(String url, UserAuthenticationDto userAuthenticationDto) {
        HttpEntity<UserAuthenticationDto> requestEntity = new HttpEntity<>(userAuthenticationDto);

        return REST_TEMPLATE.exchange(url, HttpMethod.POST, requestEntity, UserReadingDto.class);
    }

    public <T> ResponseEntity<T[]> getAll(String url, Class<T[]> receivingClass) {
        return REST_TEMPLATE.exchange(url, HttpMethod.GET, null, receivingClass);
    }

    public ResponseEntity<Void> post(String url, CreationDto creationDto) {
        HttpEntity<CreationDto> requestEntity = new HttpEntity<>(creationDto);

        return REST_TEMPLATE.exchange(url, HttpMethod.POST, requestEntity, Void.class);
    }

    public <T extends ReadingDto> ResponseEntity<T> update(String url, CreationDto creationDto, Class<T> receivingType) {
        HttpEntity<CreationDto> requestEntity = new HttpEntity<>(creationDto);

        return REST_TEMPLATE.exchange(url, HttpMethod.PUT, requestEntity, receivingType);
    }
}
