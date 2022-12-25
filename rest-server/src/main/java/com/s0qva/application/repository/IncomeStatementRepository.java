package com.s0qva.application.repository;

import com.s0qva.application.model.IncomeStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncomeStatementRepository extends JpaRepository<IncomeStatement, Long> {

    @Query(value = "SELECT * FROM income_statement " +
            "ORDER BY report_date DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<IncomeStatement> findLatest();
}
