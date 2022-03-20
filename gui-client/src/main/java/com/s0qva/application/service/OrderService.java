package com.s0qva.application.service;

import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.http.RestRequestSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Value("${rest-server.url.orders}")
    private String ordersUrl;

    public boolean createOrder(OrderCreationDto orderCreationDto) {
        ResponseEntity<Void> responseEntity = RestRequestSender.post(ordersUrl, orderCreationDto);

        return responseEntity.getStatusCode()
                .equals(HttpStatus.CREATED);
    }
}
