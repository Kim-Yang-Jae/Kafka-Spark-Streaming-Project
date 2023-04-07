package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.SubscribeRequestDTO;
import com.ssafy.tlens.api.response.ListAndCntResponseDTO;

public interface SubscribeService {

    void insert(SubscribeRequestDTO subscribeRequestDTO);
    void delete(SubscribeRequestDTO subscribeRequestDTO);
    ListAndCntResponseDTO getSubscribeReporter(Long userId);
    ListAndCntResponseDTO getNewsBySubscribeReporter(Long userId);
    boolean subscribeStatus(SubscribeRequestDTO subscribeRequestDTO);
}
