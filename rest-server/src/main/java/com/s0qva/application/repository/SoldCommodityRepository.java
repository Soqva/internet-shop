package com.s0qva.application.repository;

import com.s0qva.application.model.SoldCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoldCommodityRepository extends JpaRepository<SoldCommodity, Long> {

    @Query(value = "select s " +
            "from SoldCommodity s " +
            "where s.soldDate >= :leftBound and s.soldDate <= :rightBound")
    List<SoldCommodity> findAllDuringTime(@Param("leftBound") Long leftBound, @Param("rightBound") Long rightBound);
}
