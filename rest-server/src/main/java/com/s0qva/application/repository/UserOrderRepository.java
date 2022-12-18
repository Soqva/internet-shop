package com.s0qva.application.repository;

import com.s0qva.application.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

    List<UserOrder> findAllByUserId(Long userId);
    Optional<UserOrder> findByUserIdAndOrderId(Long userId, Long orderId);
}
