package com.ssafy.tlens.api.service;

import com.ssafy.tlens.api.request.SignUpRequestDto;
import org.springframework.http.ResponseEntity;
import com.ssafy.tlens.api.response.KeywordResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    void signUp(SignUpRequestDto member) throws Exception;

    void logout(String requestEmail, String ATK);

    ResponseEntity<?> confirm(String email, String rawPwd);

    ResponseEntity<?> update(String email, String rawPwd);

    @Transactional
    ResponseEntity<?> deleteUser(String email);

    List<KeywordResponseDTO> getKeywordByUser(Long userId);
}
