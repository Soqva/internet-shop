package com.s0qva.application.service;

import com.s0qva.application.repository.SoldCommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SoldCommodityService {
    private final SoldCommodityRepository soldCommodityRepository;
}
