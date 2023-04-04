package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.ReporterRequestDTO;
import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.ReporterInfoDTO;
import com.ssafy.tlens.entity.rdbms.Press;

import java.util.List;

public interface ReporterService {

    void insertToReporter(TrendRequestDTO trendRequestDTO);
    void updateToReporter(TrendRequestDTO trendRequestDTO);
    void deleteToReporter(Long id);
    List<ReporterInfoDTO> getReportersByPress(Long pressId);
    List<NewsInfoDTO> getNewsByReporter(String category, int pageNo, int pageSize);
    void insert(ReporterInfoDTO reporterInfoDTO, Press press);
}
