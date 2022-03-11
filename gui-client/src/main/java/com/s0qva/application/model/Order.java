package com.s0qva.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.s0qva.application.model.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user", "products"})
public class Order {
    private Long id;

    private LocalDateTime orderDate;

    @Builder.Default
    private OrderStatus status = OrderStatus.WAITING;

    @JsonBackReference
    private User user;

    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
