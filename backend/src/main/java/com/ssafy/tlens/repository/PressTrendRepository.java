package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.PressTrend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PressTrendRepository extends JpaRepository<PressTrend, Long> {
}
