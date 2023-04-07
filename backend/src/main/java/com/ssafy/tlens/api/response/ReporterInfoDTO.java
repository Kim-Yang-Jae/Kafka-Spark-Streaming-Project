package com.ssafy.tlens.api.response;

import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.entity.rdbms.Reporter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReporterInfoDTO {
    private Long reporterId;
    private String name;
    private String press;
    private String pressThumbnail;
    private String thumbnail;
    private String crawlLink;

    public ReporterInfoDTO(Reporter reporter) {
        reporterId = reporter.getReporterId();
        name = reporter.getName();
        press = reporter.getPress().getName();
        pressThumbnail = reporter.getPress().getThumbnail();
        thumbnail = reporter.getThumbnail();
        crawlLink = reporter.getCrawlLink();
    }

    @Override
    public String toString() {
        return "ReporterInfoDTO{" +
                "reporterId=" + reporterId +
                ", name='" + name + '\'' +
                ", press='" + press + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", crawlLink='" + crawlLink + '\'' +
                '}';
    }
}
