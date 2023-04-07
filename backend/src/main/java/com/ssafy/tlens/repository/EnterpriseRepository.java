package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
}
