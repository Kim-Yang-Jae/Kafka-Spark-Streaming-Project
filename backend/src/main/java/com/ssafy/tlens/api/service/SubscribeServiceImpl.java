package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.SubscribeRequestDTO;
import com.ssafy.tlens.api.response.ListAndCntResponseDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.ReporterInfoDTO;
import com.ssafy.tlens.common.exception.handler.DuplicateResourceException;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.entity.rdbms.Reporter;
import com.ssafy.tlens.entity.rdbms.User;
import com.ssafy.tlens.entity.rdbms.Subscribe;
import com.ssafy.tlens.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {

    private final UserRepository userRepository;
    private final ReporterRepository reporterRepository;
    private final SubscribeRepository subscribeRepository;
    private final NewsRepository newsRepository;
    private final PressRepository pressRepository;

    @Override
    @Transactional
    public void insert(SubscribeRequestDTO subscribeRequestDTO) {
        User user = userRepository.findById(subscribeRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + subscribeRequestDTO.getUserId()));

        Reporter reporter = reporterRepository.findById(subscribeRequestDTO.getReporterId())
                .orElseThrow(() -> new NotFoundException("Could not found reporter id : " + subscribeRequestDTO.getReporterId()));

        // 이미 구독되어있으면 에러 반환
        if (subscribeRepository.findByUserAndReporter(user, reporter).isPresent()){
            // TODO 409에러로 변경
            throw new DuplicateResourceException("already exist data by user id :" + user.getUserId() + " ,"
                    + "reporter id : " + reporter.getReporterId());
        }

        Subscribe subscribe = Subscribe.builder()
                .user(user)
                .reporter(reporter)
                .build();

        subscribeRepository.save(subscribe);
    };

    @Override
    @Transactional
    public void delete(SubscribeRequestDTO subscribeRequestDTO) {
        User user = userRepository.findById(subscribeRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + subscribeRequestDTO.getUserId()));

        Reporter reporter = reporterRepository.findById(subscribeRequestDTO.getReporterId())
                .orElseThrow(() -> new NotFoundException("Could not found reporter id : " + subscribeRequestDTO.getReporterId()));

        Subscribe subscribe = subscribeRepository.findByUserAndReporter(user, reporter)
                .orElseThrow(() -> new NotFoundException("Could not found subscribe id"));

        subscribeRepository.delete(subscribe);
    };

    @Override
    public ListAndCntResponseDTO getSubscribeReporter(Long userId) {
        List<Reporter> reporterList = reporterRepository.findSubscribeReporterByUserId(userId);

        List<ReporterInfoDTO> reporterInfoList = reporterList.stream()
                .map(reporter -> new ReporterInfoDTO(reporter))
                .collect(Collectors.toList());

        return new ListAndCntResponseDTO(reporterInfoList, reporterInfoList.size());
    }

    @Override
    public ListAndCntResponseDTO getNewsBySubscribeReporter(Long userId) {
        List<Reporter> reporters = reporterRepository.findSubscribeReporterByUserId(userId);
        List<NewsInfoDTO> newsInfoList = new ArrayList<>();

        for (Reporter reporter : reporters) {
            List<News> newses = newsRepository.findByReporter(reporter.getName());
            for (News news : newses) {
                NewsInfoDTO newsInfo = new NewsInfoDTO(news, pressRepository.findByName(news.getPress()).getThumbnail());
                newsInfoList.add(newsInfo);
            }
        }

        return new ListAndCntResponseDTO(newsInfoList, newsInfoList.size());
    }

    @Override
    public boolean subscribeStatus(SubscribeRequestDTO subscribeRequestDTO) {

        User user = userRepository.findById(subscribeRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + subscribeRequestDTO.getUserId()));

        Reporter reporter = reporterRepository.findById(subscribeRequestDTO.getReporterId())
                .orElseThrow(() -> new NotFoundException("Could not found reporter id : " + subscribeRequestDTO.getReporterId()));

        if (subscribeRepository.findByUserAndReporter(user, reporter).isPresent()) {
            return true;
        }

        return false;
    }
}