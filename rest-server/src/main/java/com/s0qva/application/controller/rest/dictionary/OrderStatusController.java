package com.s0qva.application.controller.rest.dictionary;

import com.s0qva.application.dto.OrderDto;
import com.s0qva.application.dto.dictionary.DictionaryOrderStatusDto;
import com.s0qva.application.service.DictionaryOrderStatusService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/dictionaries/order-statuses")
@SecurityRequirement(name = "api-access")
@RequiredArgsConstructor
public class OrderStatusController {
    private final DictionaryOrderStatusService dictionaryOrderStatusService;

    @GetMapping
    public ResponseEntity<List<DictionaryOrderStatusDto>> getAll() {
        var orderStatuses = dictionaryOrderStatusService.getAll();

        return ResponseEntity.ok(orderStatuses);
    }
}
