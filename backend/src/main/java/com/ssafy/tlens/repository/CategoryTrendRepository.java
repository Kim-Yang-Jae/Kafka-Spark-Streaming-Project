package com.ssafy.tlens.repository;

import com.ssafy.tlens.api.response.CategoryTrendResponseDTO;
import com.ssafy.tlens.entity.rdbms.CategoryTrend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryTrendRepository extends JpaRepository<CategoryTrend, Long> {
}
