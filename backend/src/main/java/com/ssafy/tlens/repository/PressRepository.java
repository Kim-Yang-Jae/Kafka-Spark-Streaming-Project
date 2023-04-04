package com.ssafy.tlens.repository;

import com.ssafy.tlens.entity.rdbms.Press;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PressRepository extends JpaRepository<Press, Long> {
    Press findByPressId(Long pressId);
    Press findByName(String Name);
}
