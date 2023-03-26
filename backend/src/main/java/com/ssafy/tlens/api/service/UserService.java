package com.ssafy.tlens.api.service;

import com.ssafy.tlens.dto.SignUpRequestDto;

public interface UserService {
    void signUp(SignUpRequestDto member) throws Exception;

}