package com.s0qva.application.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    USER("user", "U", "среднестатистический пользователь магазина"),
    ADMIN("admin", "A", "администратор магазина");

    private final String name;
    private final String shortName;
    private final String description;
}
