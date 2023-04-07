package com.ssafy.tlens.repository;

import com.ssafy.tlens.api.response.MorphemeResponseDTO;
import com.ssafy.tlens.entity.rdbms.Morpheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MorphemeRepository extends JpaRepository<Morpheme,Long> {
    List<Morpheme> findMorphemeByCompleted(boolean status);
}
