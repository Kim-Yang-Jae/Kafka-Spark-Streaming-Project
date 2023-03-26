package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.config.jwt.JwtProvider;
import com.ssafy.tlens.dto.SignUpRequestDto;
import com.ssafy.tlens.enums.ResponseEnum;
import com.ssafy.tlens.api.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final JwtProvider jwtProvider;

    @GetMapping
    public void test(){
        System.out.println("시작하기");
    }
    @PostMapping
    @ApiOperation(value = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(code=200,message="정상적으로 회원가입이 되었습니다.", response = ResponseEntity.class)
    })
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_JOIN_SUCCESS), HttpStatus.OK);
    }

}
