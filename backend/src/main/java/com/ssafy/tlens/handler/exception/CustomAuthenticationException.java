package com.ssafy.tlens.handler.exception;


import com.ssafy.tlens.enums.ResponseEnum;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationServiceException;

@Getter
public class CustomAuthenticationException extends AuthenticationServiceException {

    private final ResponseEnum responseEnum;

    public CustomAuthenticationException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
        System.out.println("CustomAuthenticationException 실행함,responseEnum =  " + responseEnum);
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }
}