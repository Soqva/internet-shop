package com.s0qva.application.http;

import com.s0qva.application.http.error.handler.ServerResponseErrorHandler;
import lombok.experimental.UtilityClass;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@UtilityClass
public class RestRequestSender {
    private static final RestTemplate REST_TEMPLATE;

    static {
        REST_TEMPLATE = new RestTemplateBuilder()
                .errorHandler(new ServerResponseErrorHandler())
                .build();
    }

    public <T> ResponseEntity<T[]> getAll(String url, HttpEntity<?> requestEntity, Class<T[]> receivingClass) {
        return REST_TEMPLATE.exchange(url, GET, requestEntity, receivingClass);
    }

    public <T> ResponseEntity<T> get(String url, HttpEntity<?> requestEntity, Class<T> receivingClass) {
        return REST_TEMPLATE.exchange(url, GET, requestEntity, receivingClass);
    }

    public <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> receivingClass) {
        return REST_TEMPLATE.exchange(url, POST, requestEntity, receivingClass);
    }

    public <T> ResponseEntity<T> update(String url, HttpEntity<?> requestEntity, Class<T> receivingType) {
        return REST_TEMPLATE.exchange(url, PUT, requestEntity, receivingType);
    }

    public ResponseEntity<Void> delete(String url, HttpEntity<?> requestEntity) {
        return REST_TEMPLATE.exchange(url, DELETE, requestEntity, Void.class);
    }
}
