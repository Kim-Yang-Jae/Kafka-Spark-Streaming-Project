package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.EnterpriseDetailDTO;
import com.ssafy.tlens.api.response.MainEnterpriseDTO;

import java.util.List;

public interface EnterpriseService {

    void insertToEnterprise(TrendRequestDTO trendRequestDTO);
    void updateToEnterprise(TrendRequestDTO trendRequestDTO);
    void deleteToEnterprise(Long id);
    List<MainEnterpriseDTO> getMainEnterprise();
    EnterpriseDetailDTO getEnterpriseDetail(Long enterpriseId);
}
