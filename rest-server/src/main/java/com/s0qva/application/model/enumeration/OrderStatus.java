package com.s0qva.application.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {
    ACCEPTED("accepted", "ACC", "заказ принят администратором магазина"),
    WAITING("waiting", "WTNG", "заказ ожидает проверки администратором магазина"),
    CANCELED("canceled", "CNCL", "заказ отменен администратором магазина");

    private final String name;
    private final String shortName;
    private final String description;
}
