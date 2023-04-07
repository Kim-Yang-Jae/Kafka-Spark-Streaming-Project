package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.RegionTrend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionTrendRepository extends JpaRepository<RegionTrend, Long> {
}
