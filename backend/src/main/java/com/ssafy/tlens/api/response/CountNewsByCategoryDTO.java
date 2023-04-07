package com.ssafy.tlens.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountNewsByCategoryDTO {

    private Long countAllNews;
    private Long countRecentNews;
}
