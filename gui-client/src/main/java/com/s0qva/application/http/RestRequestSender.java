package com.s0qva.application.http;

import com.s0qva.application.dto.CreationDto;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@UtilityClass
public class RestRequestSender {

    public ResponseEntity<Void> post(String url, CreationDto creationDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CreationDto> requestEntity = new HttpEntity<>(creationDto);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
    }
}
