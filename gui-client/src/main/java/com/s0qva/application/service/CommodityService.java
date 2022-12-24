package com.s0qva.application.service;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.http.RestRequestSender;
import com.s0qva.application.session.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CommodityService {
    @Value("${rest-server.url.products}")
    private String productsUrl;

    public List<CommodityDto> getAll() {
        var bearerToken = UserSession.getInstance().getBearerHttpHeader();
        var responseEntity = RestRequestSender.getAll(productsUrl, new HttpEntity<>(null, bearerToken), CommodityDto[].class);

        if (responseEntity.getBody() != null) {
            return Arrays.asList(responseEntity.getBody());
        }
        return Collections.emptyList();
    }
}
