package com.ssafy.tlens.handler.exception;


import com.ssafy.tlens.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class CustomApiException extends RuntimeException{

    private final ResponseEnum responseEnum;

    public CustomApiException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }
}