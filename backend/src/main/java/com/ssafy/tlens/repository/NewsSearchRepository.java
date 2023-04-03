package com.ssafy.tlens.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.entity.rdbms.QNews;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NewsSearchRepository {

    private final EntityManager em;

    public List<News> findBySearch(String searchword, int pageNo, int pageSize) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QNews news = QNews.news;

        return query
                .select(news)
                .from(news)
                .where(news.title.contains(searchword).or(news.summary.contains(searchword)))
                .orderBy(news.createdDate.desc())
                //offset 방식
                .limit(pageSize)
                .offset(pageNo * pageSize)
                .distinct()
                .fetch();
    }

    // 분야별 기사 조회
    public List<News> findByCategory(String category, int pageNo, int pageSize) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QNews news = QNews.news;

        return query
                .select(news)
                .from(news)
                .where(categoryEq(category))
                .orderBy(news.createdDate.desc())
                //offset 방식
                .limit(pageSize)
                .offset(pageNo * pageSize)
                .distinct()
                .fetch();
    }

    // 분야별 기사 수
    public Long countNewsByCategory(String category) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QNews news = QNews.news;

        return query
                .select(news)
                .from(news)
                .where(categoryEq(category))
                .fetchCount();
    }

    // 분야별 오늘 작성된 기사 수
    public Long countNewsByCategoryOnToday(String category) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QNews news = QNews.news;

        return query
                .select(news)
                .from(news)
                .where(categoryEq(category),isCreatedToday())
                .fetchCount();
    }

    // 전체, 분야 분기 조건
    private BooleanExpression categoryEq(String category) {
        if (category.equals("전체")) {
            return null;
        }
        return QNews.news.category.eq(category);
    }

    // 현재 시각 기준 하루 이내 조건
    private BooleanExpression isCreatedToday() {
        QNews news = QNews.news;

        LocalDateTime dateTime = LocalDateTime.now();
        // 현재시간에서 하루 전의 시간으로 세팅
        // 해당 시간 이후에 등록된 기사들만 조회
        return news.createdDate.after(dateTime.minusDays(1));
    }
}