package com.s0qva.application.service;

import com.s0qva.application.repository.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserOrderService {
    private final UserOrderRepository userOrderRepository;
}
