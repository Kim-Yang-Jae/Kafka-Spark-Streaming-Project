package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
