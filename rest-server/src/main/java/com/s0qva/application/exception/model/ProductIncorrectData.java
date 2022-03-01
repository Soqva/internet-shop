package com.s0qva.application.exception.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ProductIncorrectData {
    private final String exceptionInfo;
}
