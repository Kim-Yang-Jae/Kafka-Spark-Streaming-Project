package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.response.UserInfoResponseDTO;
import com.ssafy.tlens.api.service.MypageService;
import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.enums.ResponseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestParam Long id) {
        UserInfoResponseDTO result = mypageService.getUserInfo(id);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_INFO_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/keyword")
    public ResponseEntity<?> getUserKeyword(@RequestParam Long id) {
        List<String> result = mypageService.getUserKeyword(id);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_INFO_SUCCESS, result), HttpStatus.OK);
    }
}
