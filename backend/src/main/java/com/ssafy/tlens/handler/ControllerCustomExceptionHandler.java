package com.ssafy.tlens.handler;

import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.handler.exception.CustomApiException;
import com.ssafy.tlens.handler.exception.CustomAuthenticationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerCustomExceptionHandler {

    @ExceptionHandler(CustomAuthenticationException.class)
    public HttpEntity<?> customAuthenticationException(CustomAuthenticationException e){
        System.out.println("AuthenticationException");
        return new ResponseEntity<>(new ResponseDto<>(e.getResponseEnum()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class)
    public HttpEntity<?> apiException(CustomApiException e){
        System.out.println("apiException");
        return new ResponseEntity<>(new ResponseDto<>(e.getResponseEnum()),HttpStatus.BAD_REQUEST);
    }

}