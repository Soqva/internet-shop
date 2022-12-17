package com.s0qva.application.repository;

import com.s0qva.application.model.IncomeStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeStatementRepository extends JpaRepository<IncomeStatement, Long> {
}
