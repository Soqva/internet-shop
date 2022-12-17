package com.s0qva.application.service;

import com.s0qva.application.repository.IncomeStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IncomeStatementService {
    private final IncomeStatementRepository incomeStatementRepository;
}
