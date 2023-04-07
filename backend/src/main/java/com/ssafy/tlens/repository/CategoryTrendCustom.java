package com.ssafy.tlens.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tlens.entity.rdbms.CategoryTrend;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.ssafy.tlens.entity.rdbms.QCategoryTrend.categoryTrend;

import java.sql.Timestamp;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class CategoryTrendCustom {
    private final EntityManager em;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    public CategoryTrend getCategoryTrendByKeywordAndDateAndCategory(String keyword, Timestamp today, Timestamp tomorrow, Long categoryId) {
        return jpaQueryFactory.selectFrom(categoryTrend)
                .where(categoryTrend.keyword.containsIgnoreCase(keyword)
                        , categoryTrend.category.categoryId.eq(categoryId)
                        , categoryTrend.date.between(today, tomorrow))
                .fetchFirst();
    }

    public List<CategoryTrend> getCategoryTrendByDateAndCategoryOrderByCount(Timestamp startTime, Timestamp endTime, Long categoryId, int count){
        return jpaQueryFactory.selectFrom(categoryTrend)
                .where(categoryTrend.date.between(startTime,endTime)
                        ,eqCategoryId(categoryId)
                        ,categoryTrend.keyword.length().gt(5))
                .orderBy(categoryTrend.count.desc())
                .limit(count)
                .fetch();
    }

    private BooleanExpression eqCategoryId(Long categoryId){
        if(categoryId == null || categoryId == 0){
            return null;
        }else {
            return categoryTrend.category.categoryId.eq(categoryId);
        }
    }


}
