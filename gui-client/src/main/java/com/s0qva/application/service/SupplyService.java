package com.s0qva.application.service;

import com.s0qva.application.dto.SupplyDto;
import com.s0qva.application.http.RestRequestSender;
import com.s0qva.application.session.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SupplyService {
    @Value("${rest-server.url.supplies}")
    private String suppliesUrl;

    public boolean create(SupplyDto supplyDto) {
        var bearerToken = UserSession.getInstance().getBearerHttpHeader();
        var responseEntity = RestRequestSender.post(suppliesUrl, new HttpEntity<>(supplyDto, bearerToken), Void.class);

        return responseEntity.getStatusCode().equals(HttpStatus.CREATED);
    }
}
