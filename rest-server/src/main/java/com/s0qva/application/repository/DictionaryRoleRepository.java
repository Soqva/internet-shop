package com.s0qva.application.repository;

import com.s0qva.application.model.dictionary.DictionaryRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRoleRepository extends JpaRepository<DictionaryRole, Long> {
}
