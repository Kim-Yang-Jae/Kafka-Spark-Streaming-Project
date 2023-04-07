package com.ssafy.tlens.api.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscribeRequestDTO {

    private Long userId;
    private Long reporterId;

    public SubscribeRequestDTO(Long userId, Long reporterId) {
        this.userId = userId;
        this.reporterId = reporterId;
    }
}

