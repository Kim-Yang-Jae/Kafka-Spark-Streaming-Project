package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.Reporter;
import com.ssafy.tlens.entity.rdbms.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<Integer> deleteByEmail(String email);
    boolean existsByEmail(String userEmail);
//    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.userId in (SELECT w.user.userId FROM Subscribe w WHERE w.reporter.reporterId = :id)")
    List<User> getSubscribeUserByReporterId(@Param("id") long id);
}