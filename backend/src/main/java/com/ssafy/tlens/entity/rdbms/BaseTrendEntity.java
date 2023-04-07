package com.ssafy.tlens.entity.rdbms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTrendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trend_id")
    private Long trendId;

    @Column(name = "keyword", nullable = false, length = 50)
    private String keyword;

    @Column(name = "count")
    private int count;

    @Column(name = "created_at")
    private Timestamp date;

    public void update(String keyword, int count, Timestamp date) {
        this.keyword = keyword;
        this.count = count;
        this.date = date;
    }
}
