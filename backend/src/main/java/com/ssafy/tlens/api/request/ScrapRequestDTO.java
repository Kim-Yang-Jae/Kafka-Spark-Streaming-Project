package com.ssafy.tlens.api.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapRequestDTO {

    private Long userId;
    private Long newsId;

    public ScrapRequestDTO(Long userId, Long newsId) {
        this.userId = userId;
        this.newsId = newsId;
    }
}
