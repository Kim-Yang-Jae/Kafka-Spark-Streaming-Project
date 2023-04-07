package com.ssafy.tlens.api.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrendRequestDTO {

    private Long targetId;

    private String keyword;

    private int count;

    private Timestamp date;

}
