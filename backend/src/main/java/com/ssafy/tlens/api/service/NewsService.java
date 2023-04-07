package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.NewsRequestDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.entity.rdbms.News;

import java.util.List;

public interface NewsService {
    List<NewsInfoDTO> getNewsBySearch(String searchword, int pageNo, int pageSize);

    void insert(NewsRequestDTO newsRequestDTO);
    // 입력값으로 전달된 언론사에서 가장 최근에 작성된 뉴스 기사를 가져온다.
    News getRecentData(String press);
}
