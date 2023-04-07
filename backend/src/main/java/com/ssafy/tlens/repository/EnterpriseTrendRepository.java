package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.EnterpriseTrend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseTrendRepository extends JpaRepository<EnterpriseTrend, Long> {
}
