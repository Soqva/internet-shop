package com.s0qva.application.service;

import com.s0qva.application.model.Order;
import com.s0qva.application.model.User;
import com.s0qva.application.model.UserOrder;
import com.s0qva.application.repository.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserOrderService {
    private final UserOrderRepository userOrderRepository;

    public List<UserOrder> getAllByUserId(Long userId) {
        return userOrderRepository.findAllByUserId(userId);
    }

    public UserOrder getByUserIdAndOrderId(Long userId, Long orderId) {
        return userOrderRepository.findByUserIdAndOrderId(userId, orderId).orElse(null);
    }

    @Transactional
    public UserOrder create(Long userId, Long orderId) {
        var userOrder = mapToEntity(userId, orderId);

        return create(userOrder);
    }

    @Transactional
    public UserOrder create(UserOrder userOrder) {
        return userOrderRepository.save(userOrder);
    }

    private UserOrder mapToEntity(Long userId, Long orderId) {
        return UserOrder.builder()
                .user(User.builder().id(userId).build())
                .order(Order.builder().id(orderId).build())
                .build();
    }
}
