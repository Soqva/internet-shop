package com.s0qva.application.exception.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class IncorrectDataContainer {
    private final Map<String, String> exceptions;
}
