package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.NewsRequestDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final MorphemeRepository morphemeRepository;
    private final CategoryTrendRepository categoryTrendRepository;
    private final CategoryTrendCustom categoryTrendCustom;
    private final CategoryRepository categoryRepository;
    private final NewsSearchRepository newsSearchRepository;
    private final PressRepository pressRepository;

    // 매개변수로 전달받은 객체를 통해 기사를 RDBMS에 삽입한다.
    @Transactional
    @Override
    public void insert(NewsRequestDTO newsRequestDTO) {
        News news = News.builder()
                .title(newsRequestDTO.getTitle())
                .summary(newsRequestDTO.getSummary())
                .reporter(newsRequestDTO.getReporter())
                .press(newsRequestDTO.getPress())
                .region(newsRequestDTO.getRegion())
                .category(newsRequestDTO.getCategory())
                .enterprise(newsRequestDTO.getEnterprise())
                .thumbNail(newsRequestDTO.getThumbNail())
                .crawlLink(newsRequestDTO.getCrawlLink())
                .originalLink(newsRequestDTO.getOriginalLink())
                .createdDate(newsRequestDTO.getCreatedDate())
                .modifiedDate(newsRequestDTO.getModifiedDate())
                .build();

        newsRepository.save(news);
    }

    // 입력값으로 전달된 언론사에서 가장 최근에 작성된 뉴스 기사를 가져온다.
    @Override
    public News getRecentData(String press) {
        return newsRepository.findTopByPressOrderByCreatedDateDesc(press)
                .orElseThrow(() -> new NotFoundException("Could not found press : " + press));
    }

    @Override
    public List<NewsInfoDTO> getNewsBySearch(String searchword, int pageNo, int pageSize) {

        List<News> newses = newsSearchRepository.findBySearch(searchword, pageNo, pageSize);


        List<NewsInfoDTO> newsInfoList = newses.stream()
                .map(news -> new NewsInfoDTO(news, pressRepository.findByName(news.getPress()).getThumbnail()))
                .collect(Collectors.toList());

        return newsInfoList;
    }

}
