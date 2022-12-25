package com.s0qva.application.controller.rest.dictionary;

import com.s0qva.application.dto.dictionary.DictionaryCountryDto;
import com.s0qva.application.service.DictionaryCountryService;
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
@RequestMapping("/api/v1/dictionaries/countries")
@SecurityRequirement(name = "api-access")
@RequiredArgsConstructor
public class CountryController {
    private final DictionaryCountryService dictionaryCountryService;

    @GetMapping
    public ResponseEntity<List<DictionaryCountryDto>> getAll() {
        var orderStatuses = dictionaryCountryService.getAll();

        return ResponseEntity.ok(orderStatuses);
    }
}
