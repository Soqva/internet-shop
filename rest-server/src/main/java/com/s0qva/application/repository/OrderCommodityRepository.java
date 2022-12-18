package com.s0qva.application.repository;

import com.s0qva.application.model.OrderCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderCommodityRepository extends JpaRepository<OrderCommodity, Long> {

    Optional<OrderCommodity> findByOrderIdAndCommodityId(Long orderId, Long commodityId);
}
