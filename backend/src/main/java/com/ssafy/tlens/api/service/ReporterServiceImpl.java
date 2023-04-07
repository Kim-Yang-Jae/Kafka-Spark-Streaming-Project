package com.ssafy.tlens.api.service;


import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.ReporterInfoDTO;
import com.ssafy.tlens.api.response.SubscribeInfoResponseDTO;
import com.ssafy.tlens.api.response.WordCountDTO;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.*;
import com.ssafy.tlens.repository.*;
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
public class ReporterServiceImpl implements ReporterService {

    private final ReporterTrendRepository reporterTrendRepository;
    private final ReporterRepository reporterRepository;
    private final PressRepository pressRepository;
    private final NewsSearchRepository newsSearchRepository;
    private final NewsRepository newsRepository;
    private final ReporterRepositoryCust repositoryRepositoryCust;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void insertToReporter(TrendRequestDTO request) {

        Reporter reporter = reporterRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found reporter id : " + request.getTargetId()));

        ReporterTrend reporterTrend = ReporterTrend.builder()
                .keyword(request.getKeyword())
                .count(request.getCount())
                .date(request.getDate())
                .reporter(reporter)
                .build();

        reporterTrendRepository.save(reporterTrend);
    }

    @Override
    @Transactional
    public void updateToReporter(TrendRequestDTO request) {

        ReporterTrend trend = reporterTrendRepository.findById(request.getTargetId())
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + request.getTargetId()));

        trend.update(request.getKeyword(),request.getCount(),request.getDate());
    }

    @Override
    @Transactional
    public void deleteToReporter(Long id) {

        ReporterTrend trend = reporterTrendRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found trend id : " + id));

        reporterTrendRepository.delete(trend);
    }

    @Override
    public List<ReporterInfoDTO> getReportersByPress(Long pressId) {

        Press press = pressRepository.findById(pressId)
                .orElseThrow(() -> new NotFoundException("Could not found press id : " + pressId));

        List<Reporter> reporters = press.getReporters();

        List<ReporterInfoDTO> reporterInfoList = reporters.stream()
                .map(reporter -> new ReporterInfoDTO(reporter))
                .collect(Collectors.toList());

        return reporterInfoList;
    }

    @Override
    public List<ReporterInfoDTO> getReportersByPressOffset(String press, int pageNo, int pageSize) {

        List<Reporter> reporters = repositoryRepositoryCust.getReporterByPress(press, pageNo, pageSize);

        List<ReporterInfoDTO> reporterInfoList = reporters.stream()
                .map(reporter -> new ReporterInfoDTO(reporter))
                .collect(Collectors.toList());

        return reporterInfoList;
    }

    @Override
    public List<NewsInfoDTO> getNewsByReporter(String reporter, int pageNo, int pageSize) {
        List<News> newses = newsSearchRepository.findByReporter(reporter, pageNo, pageSize);

        return newses.stream()
                .map(news -> new NewsInfoDTO(news, pressRepository.findByName(news.getPress()).getThumbnail()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<WordCountDTO> getCategoryCountByReporterNews(String reporter) {
        List<News> newses = newsRepository.findByReporter(reporter);

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (News news : newses) {
            String category = news.getCategory();
            map.put(category, map.getOrDefault(category, 0)+1);
        }

        List<WordCountDTO> countList = new ArrayList<>();

        Iterator keySetIterator = map.keySet().iterator();

        while (keySetIterator.hasNext()) {
            String key = keySetIterator.next().toString();
            countList.add(new WordCountDTO(key,map.get(key)));
        }
        return countList;
    }

    @Override
    public SubscribeInfoResponseDTO getSubscribeInfoByReporter(Long reporterId) {
        List<User> users = userRepository.getSubscribeUserByReporterId(reporterId);

        List<ArrayList<Integer>> ageCount = new ArrayList<>();
        for (int i = 0 ; i < 2; i++){
            ageCount.add(new ArrayList<>(8));
        }
        for (int j = 0 ; j < 9; j++) {
            ageCount.get(0).add(0);
            ageCount.get(1).add(0);
        }
        for (User user : users) {
            int age = user.getAge();
            String gender = user.getGender();

            int idx = age/10;
            if (idx > 9) {
                idx = 8;
            }

            if (gender.equals("male")) {
                int nowCnt = ageCount.get(0).get(idx);
                ageCount.get(0).set(idx, nowCnt + 1);
            } else {

                int nowCnt = ageCount.get(1).get(idx);
                ageCount.get(1).set(idx, nowCnt + 1);
            }
        }
        return new SubscribeInfoResponseDTO(ageCount);
    }


    @Override
    public void insert(ReporterInfoDTO reporterInfoDTO, Press press) {
        reporterRepository.save(Reporter.builder()
                .crawlLink(reporterInfoDTO.getCrawlLink())
                .name(reporterInfoDTO.getName())
                .thumbnail(reporterInfoDTO.getThumbnail())
                .press(press)
                .build()
        );
    }
}
