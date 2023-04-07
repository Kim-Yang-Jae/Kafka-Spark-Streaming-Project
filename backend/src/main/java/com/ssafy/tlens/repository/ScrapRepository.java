package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap> findByUserAndNews(User user, News news);
}
