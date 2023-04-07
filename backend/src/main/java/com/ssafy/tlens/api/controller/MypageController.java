package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.response.UserInfoResponseDTO;
import com.ssafy.tlens.api.response.WordCountDTO;
import com.ssafy.tlens.api.service.MypageService;
import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.config.auth.PrincipalDetails;
import com.ssafy.tlens.enums.ResponseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserInfoResponseDTO result = mypageService.getUserInfo(principalDetails.getUser().getUserId());

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_INFO_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/keyword")
    public ResponseEntity<?> getUserKeyword(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<String> result = mypageService.getUserKeyword(principalDetails.getUser().getUserId());

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_INFO_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/scrap/count")
    public ResponseEntity<?> getCategoryCountByScrapNews(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<WordCountDTO> result = mypageService.getCategoryCountByScrapNews(principalDetails.getUser().getUserId());

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_INFO_SUCCESS, result), HttpStatus.OK);
    }

}
