package com.ssafy.tlens.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTrendRequestDTO {
    private String keyword;
    private int count;
    private Timestamp created_at;

    private Long categoryId;
}
