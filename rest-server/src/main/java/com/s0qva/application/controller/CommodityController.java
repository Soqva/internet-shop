package com.s0qva.application.controller;

import com.s0qva.application.service.CommodityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/commodities")
@RequiredArgsConstructor
public class CommodityController {
    private final CommodityService commodityService;
}
