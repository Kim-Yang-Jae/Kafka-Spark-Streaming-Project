package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.EnterpriseDetailDTO;
import com.ssafy.tlens.api.response.MainEnterpriseDTO;
import com.ssafy.tlens.api.response.MainPressDTO;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.Enterprise;
import com.ssafy.tlens.entity.rdbms.EnterpriseTrend;
import com.ssafy.tlens.entity.rdbms.Press;
import com.ssafy.tlens.repository.EnterpriseRepository;
import com.ssafy.tlens.repository.EnterpriseTrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseTrendRepository enterpriseTrendRepository;
    private final EnterpriseRepository enterpriseRepository;

    @Override
    @Transactional
    public void insertToEnterprise(TrendRequestDTO request) {

        Enterprise enterprise = enterpriseRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found enterprise id : " + request.getTargetId()));

        EnterpriseTrend enterpriseTrend = EnterpriseTrend.builder()
                .keyword(request.getKeyword())
                .count(request.getCount())
                .date(request.getDate())
                .enterprise(enterprise)
                .build();

        enterpriseTrendRepository.save(enterpriseTrend);
    }

    @Override
    @Transactional
    public void updateToEnterprise(TrendRequestDTO request) {

        EnterpriseTrend trend = enterpriseTrendRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + request.getTargetId()));

        trend.update(request.getKeyword(),request.getCount(),request.getDate());
    }

    @Override
    @Transactional
    public void deleteToEnterprise(Long id) {

        EnterpriseTrend trend = enterpriseTrendRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + id));

        enterpriseTrendRepository.delete(trend);
    }

    @Override
    public List<MainEnterpriseDTO> getMainEnterprise() {

        List<Enterprise> enterprises = enterpriseRepository.findAll();

        List<MainEnterpriseDTO> entepriseInfoList = enterprises.stream()
                .map(enterprise -> new MainEnterpriseDTO(enterprise))
                .collect(Collectors.toList());

        return entepriseInfoList;
    }

    @Override
    public EnterpriseDetailDTO getEnterpriseDetail(Long enterpriseId) {

        Enterprise enterprise = enterpriseRepository.findById(enterpriseId)
                .orElseThrow(() -> new NotFoundException("Colud not found enterprise id : " + enterpriseId));

        return new EnterpriseDetailDTO(enterprise);
    }
}
