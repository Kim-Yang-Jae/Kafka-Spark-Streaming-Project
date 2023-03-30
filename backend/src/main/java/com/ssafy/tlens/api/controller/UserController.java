package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.config.auth.PrincipalDetails;
import com.ssafy.tlens.config.jwt.JwtProperties;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final JwtProvider jwtProvider;

    @PostMapping
    @ApiOperation(value = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(code=200,message="정상적으로 회원가입이 되었습니다.", response = ResponseEntity.class)
    })
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_JOIN_SUCCESS), HttpStatus.OK);
    }
    @GetMapping("/reissue")
    public ResponseEntity<?> reissue(
            @AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request
    ) {
        System.out.println(principalDetails.getUser().getUserId() + " " + principalDetails.getUser().getEmail());
        String reqRTK = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

        String atk= JwtProperties.TOKEN_PREFIX+jwtProvider.reissueAtk(principalDetails.getUser(), reqRTK);
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.ATK_REISSUE_SUCCESS, atk), HttpStatus.OK);
    }
    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request) {
        System.out.println("logout 진입");
        System.out.println(request);
        System.out.println(request.getHeader(JwtProperties.HEADER_STRING));
        String reqATK = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");
        userService.logout(principalDetails.getUser().getEmail(), reqATK);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_LOGOUT_SUCCESS), HttpStatus.OK);
    }
}
