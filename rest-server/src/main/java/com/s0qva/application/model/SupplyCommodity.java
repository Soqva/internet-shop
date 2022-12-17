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
@Table(name = "supply_commodity")
public class SupplyCommodity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "supply_id")
    private Supply supply;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "commodity_id")
    private Commodity commodity;

    @Column(name = "amount_of_supplied_commodities")
    private Integer amountOfSuppliedCommodities;

    public SupplyCommodity(Long id, Supply supply, Commodity commodity, Integer amountOfSuppliedCommodities) {
        this.id = id;
        this.amountOfSuppliedCommodities = amountOfSuppliedCommodities;

        setSupply(supply);
        setCommodity(commodity);
    }

    public void setSupply(Supply supply) {
        this.supply = supply;

        supply.getSupplyCommodities().add(this);
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;

        commodity.getSupplyCommodities().add(this);
    }
}
