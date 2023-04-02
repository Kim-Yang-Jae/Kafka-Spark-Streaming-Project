package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.MainCategoryDTO;
import com.ssafy.tlens.api.response.MainEnterpriseDTO;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.Category;
import com.ssafy.tlens.entity.rdbms.CategoryTrend;
import com.ssafy.tlens.repository.CategoryRepository;
import com.ssafy.tlens.repository.CategoryTrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private  final CategoryTrendRepository categoryTrendRepository;
    private  final CategoryRepository categoryRepository;

    public void insertToCategory(TrendRequestDTO request) {

        Category category = categoryRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found category id : " + request.getTargetId()));

        CategoryTrend categoryTrend = CategoryTrend.builder()
                .keyword(request.getKeyword())
                .count(request.getCount())
                .date(request.getDate())
                .category(category)
                .build();

        categoryTrendRepository.save(categoryTrend);
    }

    public void updateToCategory(TrendRequestDTO request) {

        CategoryTrend trend = categoryTrendRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + request.getTargetId()));

        trend.update(request.getKeyword(),request.getCount(),request.getDate());
    }

    public void deleteToCategory(Long id) {

        CategoryTrend trend = categoryTrendRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + id));

        categoryTrendRepository.delete(trend);
    }

    public List<MainCategoryDTO> getMainCategory() {

        List<Category> categories = categoryRepository.findAll();

        List<MainCategoryDTO> categoryInfoList = categories.stream()
                .map(category -> new MainCategoryDTO(category))
                .collect(Collectors.toList());

        return categoryInfoList;
    }

}
