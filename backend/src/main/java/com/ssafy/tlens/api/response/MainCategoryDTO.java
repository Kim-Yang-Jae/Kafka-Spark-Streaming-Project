package com.ssafy.tlens.api.response;

import com.ssafy.tlens.entity.rdbms.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MainCategoryDTO {

    private Long categoryId;
    private String name;

    public MainCategoryDTO(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
    }
}
