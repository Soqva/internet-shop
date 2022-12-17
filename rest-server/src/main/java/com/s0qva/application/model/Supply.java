package com.s0qva.application.model;

import com.s0qva.application.model.dictionary.DictionarySupplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@Table(name = "supply")
public class Supply {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "supplier_id")
    private DictionarySupplier supplier;

    @Column(name = "receiving_date")
    private Long receivingDate;

    @OneToMany(mappedBy = "supply")
    @Builder.Default
    @ToString.Exclude
    private List<SupplyCommodity> supplyCommodities = new ArrayList<>();
}
