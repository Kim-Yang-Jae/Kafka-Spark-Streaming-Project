package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.RegionResponseDTO;
import com.ssafy.tlens.repository.RegionRepository;
import com.ssafy.tlens.repository.RegionTrendRepository;

import java.util.List;

public interface RegionService {

    void insertToRegion(TrendRequestDTO trendRequestDTO);
    void updateToRegion(TrendRequestDTO trendRequestDTO);
    void deleteToRegion(Long id);
    List<RegionResponseDTO> getMainRegion();
    List<NewsInfoDTO> getNewsByRegion(String region, int pageNo, int pageSize);
}
