package com.s0qva.application.service;

import com.s0qva.application.dto.order.OrderCreationDto;
import com.s0qva.application.dto.order.OrderReadingDto;
import com.s0qva.application.http.RestRequestSender;
import com.s0qva.application.session.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {
    @Value("${rest-server.url.orders}")
    private String ordersUrl;
    @Value("${rest-server.url.users.orders}")
    private String ordersForSpecificUserUrl;

    public List<OrderReadingDto> getAllOrdersForSpecificUser(Long id) {
        String url = String.format(ordersForSpecificUserUrl, id);
        ResponseEntity<OrderReadingDto[]> responseEntity = RestRequestSender.getAll(url, OrderReadingDto[].class);

        if (responseEntity.getBody() != null) {
            return Arrays.asList(responseEntity.getBody());
        }

        return Collections.emptyList();
    }

    public List<OrderReadingDto> getAllOrders() {
        ResponseEntity<OrderReadingDto[]> responseEntity = RestRequestSender.getAll(ordersUrl, OrderReadingDto[].class);

        if (responseEntity.getBody() != null) {
            return Arrays.asList(responseEntity.getBody());
        }

        return Collections.emptyList();
    }

    public boolean createOrder(OrderCreationDto orderCreationDto) {
        ResponseEntity<Void> responseEntity = RestRequestSender.post(ordersUrl, orderCreationDto);
        return responseEntity.getStatusCode()
                .equals(HttpStatus.CREATED);
    }
}
