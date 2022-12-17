package com.s0qva.application.repository;

import com.s0qva.application.model.OrderCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCommodityRepository extends JpaRepository<OrderCommodity, Long> {
}
