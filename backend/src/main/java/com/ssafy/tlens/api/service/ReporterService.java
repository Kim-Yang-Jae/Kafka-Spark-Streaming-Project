package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.ReporterRequestDTO;
import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.ReporterInfoDTO;
import com.ssafy.tlens.api.response.SubscribeInfoResponseDTO;
import com.ssafy.tlens.api.response.WordCountDTO;
import com.ssafy.tlens.entity.rdbms.Press;

import java.util.List;

public interface ReporterService {

    void insertToReporter(TrendRequestDTO trendRequestDTO);
    void updateToReporter(TrendRequestDTO trendRequestDTO);
    void deleteToReporter(Long id);
    List<ReporterInfoDTO> getReportersByPress(Long pressId);
    List<ReporterInfoDTO> getReportersByPressOffset(String press, int pageNo, int pageSize);
    List<NewsInfoDTO> getNewsByReporter(String reporter, int pageNo, int pageSize);
    void insert(ReporterInfoDTO reporterInfoDTO, Press press);
    List<WordCountDTO> getCategoryCountByReporterNews(String reporter);
    SubscribeInfoResponseDTO getSubscribeInfoByReporter(Long reporterId);
}
