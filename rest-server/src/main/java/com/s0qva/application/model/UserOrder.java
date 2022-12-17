package com.s0qva.application.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "user_order")
public class UserOrder {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public UserOrder(Long id, User user, Order order) {
        this.id = id;

        setUser(user);
        setOrder(order);
    }

    public void setUser(User user) {
        this.user = user;

        user.getUserOrders().add(this);
    }

    public void setOrder(Order order) {
        this.order = order;

        order.getUserOrders().add(this);
    }
}
