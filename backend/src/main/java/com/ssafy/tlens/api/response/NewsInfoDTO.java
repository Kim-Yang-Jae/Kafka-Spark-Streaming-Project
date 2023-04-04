package com.ssafy.tlens.api.response;

import com.ssafy.tlens.entity.rdbms.News;
import lombok.*;

@Getter
@NoArgsConstructor
public class NewsInfoDTO {

    private String title;
    private String summary;
    private String reporter;
    private String link;
    private String thumbnail;
    private String category;
    public NewsInfoDTO(News news) {
        title = news.getTitle();
        summary = news.getSummary();
        reporter = news.getReporter();
        link = news.getOriginalLink();
        thumbnail = news.getThumbNail();
        category = news.getCategory();
    }
}
