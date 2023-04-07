package com.ssafy.tlens.entity.rdbms;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class News extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long newsId;

    @Column(name = "title" , length = 200)
    @NotNull
    private String title;

    @Column(name = "summary", length = 3000)
    private String summary;

    @Column(name = "reporter" , length = 50)
    private String reporter;

    @Column(name = "press" , length = 50)
    private String press;

    @Column(name = "region" , length = 100)
    private String region;

    @Column(name = "category" , length = 50)
    private String category;

    @Column(name = "enterprise" , length = 50)
    private String enterprise;

    @Column(name = "thumbnail" , length = 300)
    private String thumbNail;

    @Column(name = "crawl_link", unique = true)
    private String crawlLink;

    @Column(name = "original_link")
    private String originalLink;

    @Builder.Default
    @OneToMany(mappedBy = "news")
    private List<Scrap> scraps = new ArrayList<>();

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", reporter='" + reporter + '\'' +
                ", press='" + press + '\'' +
                ", region='" + region + '\'' +
                ", category='" + category + '\'' +
                ", enterprise='" + enterprise + '\'' +
                ", thumbNail='" + thumbNail + '\'' +
                ", crawlLink='" + crawlLink + '\'' +
                ", originalLink='" + originalLink + '\'' +
                ", scraps=" + scraps +
                '}';
    }
}