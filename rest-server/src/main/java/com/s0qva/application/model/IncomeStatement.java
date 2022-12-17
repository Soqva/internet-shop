package com.s0qva.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "income_statement")
public class IncomeStatement {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "amount_of_sold_commodities")
    private Integer amountOfSoldCommodities;

    @Column(name = "income_amount")
    private Double incomeAmount;

    @Column(name = "report_date")
    private Long reportDate;
}
