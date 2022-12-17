package com.s0qva.application.repository;

import com.s0qva.application.model.dictionary.DictionarySupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionarySupplierRepository extends JpaRepository<DictionarySupplier, Long> {
}
