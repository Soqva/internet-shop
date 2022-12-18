package com.s0qva.application.controller;

import com.s0qva.application.dto.CommodityDto;
import com.s0qva.application.service.CommodityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/commodities")
@RequiredArgsConstructor
public class CommodityController {
    private final CommodityService commodityService;

    @GetMapping
    public ResponseEntity<List<CommodityDto>> getAll() {
        var commodities = commodityService.getAll();

        return ResponseEntity.ok(commodities);
    }
}
