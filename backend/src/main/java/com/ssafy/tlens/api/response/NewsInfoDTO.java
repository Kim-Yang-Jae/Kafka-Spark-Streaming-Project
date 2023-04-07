package com.ssafy.tlens.api.response;

import com.ssafy.tlens.entity.rdbms.News;
import lombok.*;

@Getter
@NoArgsConstructor
public class NewsInfoDTO {

    private Long newsId;
    private String title;
    private String summary;
    private String reporter;
    private String link;
    private String thumbnail;
    private String category;
    private String pressThumbnail;
    public NewsInfoDTO(News news, String url) {
        newsId = news.getNewsId();
        title = news.getTitle();
        summary = news.getSummary();
        reporter = news.getReporter();
        link = news.getOriginalLink();
        thumbnail = news.getThumbNail();
        category = news.getCategory();
        pressThumbnail = url;
    }
}
