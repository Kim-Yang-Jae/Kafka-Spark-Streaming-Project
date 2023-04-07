package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.CountNewsByCategoryDTO;
import com.ssafy.tlens.api.response.MainCategoryDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CategoryService {

    void insertToCategory(TrendRequestDTO trendRequestDTO);
    void updateToCategory(TrendRequestDTO trendRequestDTO);
    void deleteToCategory(Long id);
    List<MainCategoryDTO> getMainCategory();
    List<NewsInfoDTO> getNewsByCategory(String category, int pageNo, int pageSize);

    CountNewsByCategoryDTO countNewsByCategory(String category);
}
