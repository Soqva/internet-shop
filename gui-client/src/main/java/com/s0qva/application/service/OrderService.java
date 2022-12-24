package com.s0qva.application.service;

import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.dto.dictionary.DictionaryOrderStatusDto;
import com.s0qva.application.http.RestRequestSender;
import com.s0qva.application.session.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {
    @Value("${rest-server.url.orders}")
    private String ordersUrl;
    @Value("${rest-server.url.users.orders}")
    private String ordersByUserIdUrl;
    @Value("${rest-server.url.dictionaries.order-statuses}")
    private String orderStatuses;

    public List<OrderDto> getAllByUserId(Long id) {
        return getOrders(String.format(ordersByUserIdUrl, id));
    }

    public List<OrderDto> getAll() {
        return getOrders(ordersUrl);
    }

    private List<OrderDto> getOrders(String url) {
        var bearerToken = UserSession.getInstance().getBearerHttpHeader();
        var responseEntity = RestRequestSender.getAll(url, new HttpEntity<>(null, bearerToken), OrderDto[].class);

        if (responseEntity.getBody() != null) {
            return Arrays.asList(responseEntity.getBody());
        }
        return Collections.emptyList();
    }

    public List<DictionaryOrderStatusDto> getStatuses() {
        var bearerToken = UserSession.getInstance().getBearerHttpHeader();
        var responseEntity = RestRequestSender.getAll(orderStatuses, new HttpEntity<>(null, bearerToken), DictionaryOrderStatusDto[].class);

        if (responseEntity.getBody() != null) {
            return Arrays.asList(responseEntity.getBody());
        }
        return Collections.emptyList();
    }

    public boolean create(OrderDto orderDto) {
        var bearerToken = UserSession.getInstance().getBearerHttpHeader();
        var responseEntity = RestRequestSender.post(ordersUrl, new HttpEntity<>(orderDto, bearerToken), Void.class);

        return responseEntity.getStatusCode().equals(HttpStatus.CREATED);
    }

    public OrderDto updateStatus(Long id, OrderDto orderDto) {
        var url = ordersUrl + "/" + id;
        var bearerToken = UserSession.getInstance().getBearerHttpHeader();
        var responseEntity = RestRequestSender.update(url, new HttpEntity<>(orderDto, bearerToken), OrderDto.class);

        if (responseEntity.getBody() != null) {
            return responseEntity.getBody();
        }
        return new OrderDto();
    }
}
