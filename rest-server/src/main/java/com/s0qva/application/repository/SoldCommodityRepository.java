package com.s0qva.application.repository;

import com.s0qva.application.model.SoldCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldCommodityRepository extends JpaRepository<SoldCommodity, Long> {
}
