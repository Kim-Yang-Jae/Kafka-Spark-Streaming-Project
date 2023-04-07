package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.RegionResponseDTO;
import com.ssafy.tlens.api.response.WordCountDTO;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.entity.rdbms.Region;
import com.ssafy.tlens.entity.rdbms.RegionTrend;
import com.ssafy.tlens.repository.NewsSearchRepository;
import com.ssafy.tlens.repository.PressRepository;
import com.ssafy.tlens.repository.RegionRepository;
import com.ssafy.tlens.repository.RegionTrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionTrendRepository regionTrendRepository;
    private final RegionRepository regionRepository;
    private final NewsSearchRepository newsSearchRepository;
    private final PressRepository pressRepository;

    @Override
    @Transactional
    public void insertToRegion(TrendRequestDTO request) {

        Region region = regionRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found region id : " + request.getTargetId()));

        RegionTrend regionTrend = RegionTrend.builder()
                .keyword(request.getKeyword())
                .count(request.getCount())
                .date(request.getDate())
                .region(region)
                .build();

        regionTrendRepository.save(regionTrend);
    }

    @Override
    @Transactional
    public void updateToRegion(TrendRequestDTO request) {

        RegionTrend trend = regionTrendRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + request.getTargetId()));

        trend.update(request.getKeyword(),request.getCount(),request.getDate());
    }

    @Override
    @Transactional
    public void deleteToRegion(Long id) {

        RegionTrend trend = regionTrendRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + id));

        regionTrendRepository.delete(trend);
    }

    @Override
    @Transactional
    public List<RegionResponseDTO> getMainRegion() {

        List<Region> regions = regionRepository.findAll();

        List<RegionResponseDTO> regionInfoList = regions.stream().map(region -> {
            String topKeyword = new String();
            int topKeywordCnt = -1;

            List<RegionTrend> regionTrendList = region.getTrends();

            for (RegionTrend keyword : regionTrendList) {
                if (keyword.getCount() > topKeywordCnt) {
                    topKeywordCnt = keyword.getCount();
                    topKeyword = keyword.getKeyword();
                }
            }

            Long newCnt = newsSearchRepository.countNewsByRegion(region.getName());
            RegionResponseDTO dto = new RegionResponseDTO(region, newCnt, topKeyword, topKeywordCnt);

            return dto;
        }).collect(Collectors.toList());

        return regionInfoList;
    }

    @Override
    public List<NewsInfoDTO> getNewsByRegion(String region, int pageNo, int pageSize) {
        List<News> newses = newsSearchRepository.findByRegion(region, pageNo, pageSize);

        return newses.stream()
                .map(news -> new NewsInfoDTO(news, pressRepository.findByName(news.getPress()).getThumbnail()))
                .collect(Collectors.toList());
    }

}
