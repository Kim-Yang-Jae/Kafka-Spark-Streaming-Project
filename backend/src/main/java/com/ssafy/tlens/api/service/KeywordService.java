package com.ssafy.tlens.api.service;

public interface KeywordService {

    void insert(Long userId, String keyword);
    void delete(Long userId, String keyword);
    Boolean getKeywordStatusByUser(Long userId, String keyword);
}
