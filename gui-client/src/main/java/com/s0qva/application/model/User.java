package com.s0qva.application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.s0qva.application.model.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"orders"})
public class User {
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    @Builder.Default
    private UserRole role = UserRole.USER;

    private boolean banned;

    @JsonManagedReference
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }
}
