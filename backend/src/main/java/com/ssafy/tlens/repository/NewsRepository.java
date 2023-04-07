package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.News;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n WHERE n.newsId in (SELECT w.news.newsId FROM Scrap w WHERE w.user.userId = :id)")
    List<News> findScrapNewsByUserId(@Param("id") long id);
    List<News> findByReporter(String reporter);
    News getNewsByCrawlLink(String link);
    Optional<News> findTopByPressOrderByCreatedDateDesc(String press);
}
