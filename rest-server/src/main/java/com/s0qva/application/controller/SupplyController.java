package com.s0qva.application.controller;

import com.s0qva.application.dto.SupplyDto;
import com.s0qva.application.service.SupplyService;
import com.s0qva.application.util.ServletUriUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Slf4j
@RequestMapping("/api/v1/supplies")
@RequiredArgsConstructor
public class SupplyController {
    private final SupplyService supplyService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody SupplyDto supplyDto) {
        var createdSupplyId = supplyService.create(supplyDto).getId();
        var location = ServletUriUtil.getUriFromCurrentRequest("/{id}", createdSupplyId);

        return ResponseEntity.created(location).build();
    }
}
