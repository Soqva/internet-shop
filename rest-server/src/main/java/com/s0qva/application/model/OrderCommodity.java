package com.s0qva.application.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
@Table(name = "order_commodity")
public class OrderCommodity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "commodity_id")
    private Commodity commodity;

    @Column(name = "amount_of_bought_commodity")
    private Integer amountOfBoughtCommodities;

    public OrderCommodity(Long id, Order order, Commodity commodity, Integer amountOfBoughtCommodities) {
        this.id = id;
        this.order = order;
        this.amountOfBoughtCommodities = amountOfBoughtCommodities;

        setOrder(order);
        setCommodity(commodity);
    }

    public void setOrder(Order order) {
        this.order = order;

        order.getOrderCommodities().add(this);
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;

        commodity.getOrderCommodities().add(this);
    }
}
