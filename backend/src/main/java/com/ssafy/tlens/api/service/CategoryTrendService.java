package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.response.CategoryTrendResponseDTO;

import java.util.List;

public interface CategoryTrendService {
    void countKeyword();

    List<CategoryTrendResponseDTO> getCategoryTrends(String category, int count);
}
