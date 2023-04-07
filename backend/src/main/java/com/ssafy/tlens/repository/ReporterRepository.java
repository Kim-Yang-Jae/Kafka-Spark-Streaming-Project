package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.Reporter;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReporterRepository extends JpaRepository<Reporter, Long> {

    @Query("SELECT r FROM Reporter r WHERE r.reporterId in (SELECT w.reporter.reporterId FROM Subscribe w WHERE w.user.userId = :id)")
    List<Reporter> findSubscribeReporterByUserId(@Param("id") long id);
}
