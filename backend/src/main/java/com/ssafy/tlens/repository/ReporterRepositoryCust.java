package com.ssafy.tlens.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tlens.entity.rdbms.QReporter;
import com.ssafy.tlens.entity.rdbms.Reporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReporterRepositoryCust {

    private final EntityManager em;

    public List<Reporter> getReporterByPress(String press, int pageNo, int pageSize) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QReporter reporter = QReporter.reporter;

        return query
                .select(reporter)
                .from(reporter)
                .where(reporter.press.name.eq(press))
                .orderBy(reporter.name.asc())
                //offset 방식
                .limit(pageSize)
                .offset(pageNo * pageSize)
                .distinct()
                .fetch();
    }
}
