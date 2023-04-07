package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("SELECT k FROM Keyword k WHERE k.user.userId = :userId AND k.name = :name")
    Optional<Keyword> findByUserAndName(@Param("userId") Long userId, @Param("name") String name);
}
