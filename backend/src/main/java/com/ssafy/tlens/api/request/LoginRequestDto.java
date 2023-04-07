package com.ssafy.tlens.api.request;

import com.ssafy.tlens.enums.LoginType;
import lombok.Data;

@Data
public class LoginRequestDto {
//    private LoginType loginType;
//    private String token;
    private String email;
    private String password;
}