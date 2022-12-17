package com.s0qva.application.repository;

import com.s0qva.application.model.dictionary.DictionaryOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryOrderStatusRepository extends JpaRepository<DictionaryOrderStatus, Long> {
}
