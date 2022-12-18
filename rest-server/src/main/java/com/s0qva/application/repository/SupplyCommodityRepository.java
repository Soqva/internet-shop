package com.s0qva.application.repository;

import com.s0qva.application.model.SupplyCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplyCommodityRepository extends JpaRepository<SupplyCommodity, Long> {

    Optional<SupplyCommodity> findBySupplyIdAndCommodityId(Long supplyId, Long commodityId);
}
