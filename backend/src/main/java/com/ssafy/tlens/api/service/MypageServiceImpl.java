package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.UserInfoResponseDTO;
import com.ssafy.tlens.api.response.WordCountDTO;
import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.Keyword;
import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.entity.rdbms.User;
import com.ssafy.tlens.repository.NewsRepository;
import com.ssafy.tlens.repository.UserRepository;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    @Override
    public UserInfoResponseDTO getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + id));

        return new UserInfoResponseDTO(user);
    }

    @Override
    public List<String> getUserKeyword(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + id));

        List<Keyword> keywordList = user.getKeywords();

        List<String> keywordNameList = keywordList.stream()
                .map(keyword -> keyword.getName())
                .collect(Collectors.toList());

        return keywordNameList;
    }

    @Override
    public List<WordCountDTO> getCategoryCountByScrapNews(Long id) {
        List<News> newses = newsRepository.findScrapNewsByUserId(id);

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
}
