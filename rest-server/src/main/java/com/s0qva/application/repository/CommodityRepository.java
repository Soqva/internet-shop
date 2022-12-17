package com.s0qva.application.repository;

import com.s0qva.application.model.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Long> {

    List<Commodity> findAllByIdIn(Set<Long> ids);
}
