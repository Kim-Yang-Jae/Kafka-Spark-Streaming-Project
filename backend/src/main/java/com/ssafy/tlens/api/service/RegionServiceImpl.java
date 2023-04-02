package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.Region;
import com.ssafy.tlens.entity.rdbms.RegionTrend;
import com.ssafy.tlens.repository.RegionRepository;
import com.ssafy.tlens.repository.RegionTrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionTrendRepository regionTrendRepository;
    private final RegionRepository regionRepository;

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

    public void updateToRegion(TrendRequestDTO request) {

        RegionTrend trend = regionTrendRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + request.getTargetId()));

        trend.update(request.getKeyword(),request.getCount(),request.getDate());
    }

    public void deleteToRegion(Long id) {

        RegionTrend trend = regionTrendRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + id));

        regionTrendRepository.delete(trend);
    }
}
