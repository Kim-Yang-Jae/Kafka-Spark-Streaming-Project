package com.ssafy.tlens.api.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MorphemeRequestDTO {
    String newsId;
    String title;
    String word;
    Boolean completed;
}
