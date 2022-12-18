package com.s0qva.application.model;

import com.s0qva.application.model.dictionary.DictionaryCountry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.HashCodeExclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commodity")
public class Commodity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private Double cost;

    @Column(name = "available_amount")
    private Integer availableAmount;

    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "producing_country_id")
    @ToString.Exclude
    private DictionaryCountry producingCountry;

    @OneToMany(mappedBy = "commodity")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderCommodity> orderCommodities = new ArrayList<>();

    @OneToMany(mappedBy = "commodity")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<SupplyCommodity> supplyCommodities = new ArrayList<>();
}
