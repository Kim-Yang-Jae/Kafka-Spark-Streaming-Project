package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.MainPressDTO;
import com.ssafy.tlens.entity.rdbms.Press;

import java.util.List;
import java.util.Optional;

public interface PressService {
    void insertToPress(TrendRequestDTO trendRequestDTO);
    void updateToPress(TrendRequestDTO trendRequestDTO);
    void deleteToPress(Long id);
    List<MainPressDTO> getMainPress();
    void settingPress();
    Press getPress(long pressId);
}
