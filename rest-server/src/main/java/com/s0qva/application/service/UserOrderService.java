package com.s0qva.application.service;

import com.s0qva.application.model.Order;
import com.s0qva.application.model.User;
import com.s0qva.application.model.UserOrder;
import com.s0qva.application.repository.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserOrderService {
    private final UserOrderRepository userOrderRepository;

    @Transactional
    public UserOrder create(Long userId, Long orderId) {
        var userOrder = UserOrder.builder()
                .user(User.builder().id(userId).build())
                .order(Order.builder().id(orderId).build())
                .build();
        return userOrderRepository.save(userOrder);
    }
}
