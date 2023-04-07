package com.ssafy.tlens.api.service;

import com.google.common.collect.FluentIterable;
import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.CountNewsByCategoryDTO;
import com.ssafy.tlens.api.response.MainCategoryDTO;
import com.ssafy.tlens.api.response.MainEnterpriseDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.Category;
import com.ssafy.tlens.entity.rdbms.CategoryTrend;
import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.repository.CategoryRepository;
import com.ssafy.tlens.repository.CategoryTrendRepository;
import com.ssafy.tlens.repository.NewsSearchRepository;
import com.ssafy.tlens.repository.PressRepository;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private  final CategoryTrendRepository categoryTrendRepository;
    private  final CategoryRepository categoryRepository;
    private  final NewsSearchRepository newsSearchRepository;
    private  final PressRepository pressRepository;

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void updateToCategory(TrendRequestDTO request) {

        CategoryTrend trend = categoryTrendRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + request.getTargetId()));

        trend.update(request.getKeyword(),request.getCount(),request.getDate());
    }

    @Override
    @Transactional
    public void deleteToCategory(Long id) {

        CategoryTrend trend = categoryTrendRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + id));

        categoryTrendRepository.delete(trend);
    }

    @Override
    public List<MainCategoryDTO> getMainCategory() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> new MainCategoryDTO(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsInfoDTO> getNewsByCategory(String category, int pageNo, int pageSize) {
        List<News> newses = newsSearchRepository.findByCategory(category, pageNo, pageSize);

        return newses.stream()
                .map(news -> new NewsInfoDTO(news, pressRepository.findByName(news.getPress()).getThumbnail()))
                .collect(Collectors.toList());
    }

    @Override
    public CountNewsByCategoryDTO countNewsByCategory(String category) {
        Long countAllNews = newsSearchRepository.countNewsByCategory(category);
        Long countRecentNews = newsSearchRepository.countNewsByCategoryOnToday(category);
        return new CountNewsByCategoryDTO(countAllNews, countRecentNews);
    }

}
