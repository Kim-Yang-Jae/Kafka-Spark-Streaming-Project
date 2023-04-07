package com.ssafy.tlens.api.response;

import com.ssafy.tlens.entity.rdbms.Keyword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordResponseDTO {

    private Long keywordId;
    private String name;

    public KeywordResponseDTO(Keyword keyword) {
        this.keywordId = keyword.getKeywordId();
        this.name = keyword.getName();
    }
}
