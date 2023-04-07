package com.ssafy.tlens.api.service;

import com.ssafy.tlens.common.exception.handler.NotFoundException;
import com.ssafy.tlens.entity.rdbms.Keyword;
import com.ssafy.tlens.entity.rdbms.User;
import com.ssafy.tlens.repository.KeywordRepository;
import com.ssafy.tlens.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordSerivceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void insert(Long userId, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        Keyword keyword = Keyword.builder()
                .name(name)
                .user(user)
                .build();

        keywordRepository.save(keyword);
    }

    @Override
    @Transactional
    public void delete(Long userId, String Keyword) {

        Keyword keyword = keywordRepository.findByUserAndName(userId, Keyword)
                .orElseThrow(() -> new NotFoundException("Could not found keyword"));

        keywordRepository.delete(keyword);
    }

    @Override
    public Boolean getKeywordStatusByUser(Long userId, String keyword) {

        if (keywordRepository.findByUserAndName(userId, keyword).isPresent()) {
            return true;
        }

        return false;
    }

}
