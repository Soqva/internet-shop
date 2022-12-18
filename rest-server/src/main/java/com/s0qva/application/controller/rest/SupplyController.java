package com.s0qva.application.controller.rest;

import com.s0qva.application.dto.SupplyDto;
import com.s0qva.application.service.SupplyService;
import com.s0qva.application.util.ServletUriUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/supplies")
@SecurityRequirement(name = "api-access")
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
