package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.Category;
import com.ssafy.tlens.entity.rdbms.CategoryTrend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getCategoryByName(String name);
}
