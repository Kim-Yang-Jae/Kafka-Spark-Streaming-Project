package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.News;
import com.ssafy.tlens.entity.rdbms.Reporter;
import com.ssafy.tlens.entity.rdbms.User;
import com.ssafy.tlens.entity.rdbms.Subscribe;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Optional<Subscribe> findByUserAndReporter(User user, Reporter reporter);


}
