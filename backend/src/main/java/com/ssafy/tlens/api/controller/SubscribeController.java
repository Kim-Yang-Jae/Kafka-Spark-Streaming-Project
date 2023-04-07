package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.request.ScrapRequestDTO;
import com.ssafy.tlens.api.response.ListAndCntResponseDTO;
import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.common.model.response.HttpResponseEntity.ResponseResult;
import com.ssafy.tlens.api.request.SubscribeRequestDTO;
import com.ssafy.tlens.api.service.SubscribeService;
import com.ssafy.tlens.config.auth.PrincipalDetails;
import com.ssafy.tlens.enums.ResponseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import static com.ssafy.tlens.common.model.response.HttpResponseEntity.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/subscribe")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping
    public ResponseResult<?> insert(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Long reporterId) throws Exception {
        SubscribeRequestDTO subscribeRequestDTO = new SubscribeRequestDTO(principalDetails.getUser().getUserId(), reporterId);
        subscribeService.insert(subscribeRequestDTO);
        return success();
    }

    @DeleteMapping
    public ResponseResult<?> delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Long reporterId) {
        SubscribeRequestDTO subscribeRequestDTO = new SubscribeRequestDTO(principalDetails.getUser().getUserId(), reporterId);
        subscribeService.delete(subscribeRequestDTO);
        return success();
    }

    @GetMapping("/reporter")
    public ResponseEntity<?> getSubscribeReporter(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        ListAndCntResponseDTO result = subscribeService.getSubscribeReporter(principalDetails.getUser().getUserId());

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_INFO_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/reporter/news")
    public ResponseEntity<?> getNewsBySubscribeReporter(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        ListAndCntResponseDTO result = subscribeService.getNewsBySubscribeReporter(principalDetails.getUser().getUserId());

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_INFO_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<?> subscribeStatus(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Long reporterId) {
        SubscribeRequestDTO subscribeRequestDTO = new SubscribeRequestDTO(principalDetails.getUser().getUserId(),reporterId);
        boolean result = subscribeService.subscribeStatus(subscribeRequestDTO);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_INFO_SUCCESS, result), HttpStatus.OK);
    }
}
