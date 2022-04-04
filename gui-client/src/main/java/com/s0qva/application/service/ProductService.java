package com.s0qva.application.service;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.http.RestRequestSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {
    @Value("${rest-server.url.products}")
    private String productsUrl;

    public List<ProductReadingDto> getAllProducts() {
        ResponseEntity<ProductReadingDto[]> responseEntity = RestRequestSender.getAll(productsUrl, ProductReadingDto[].class);

        if (responseEntity.getBody() != null) {
            return Arrays.asList(responseEntity.getBody());
        }

        return Collections.emptyList();
    }

    public boolean createProduct(ProductCreationDto productCreationDto) {
        ResponseEntity<Void> responseEntity = RestRequestSender.post(productsUrl, productCreationDto);

        return responseEntity.getStatusCode()
                .equals(HttpStatus.CREATED);
    }
}
