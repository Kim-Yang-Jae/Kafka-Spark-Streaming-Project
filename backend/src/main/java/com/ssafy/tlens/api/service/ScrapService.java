package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.ScrapRequestDTO;
import com.ssafy.tlens.api.response.ListAndCntResponseDTO;

public interface ScrapService {

    void insert(ScrapRequestDTO scrapRequestDTO);
    void delete(ScrapRequestDTO scrapRequestDTO);
    ListAndCntResponseDTO getScrapNewsList(Long userId);
    boolean scrapStatus(ScrapRequestDTO scrapRequestDTO);
}
