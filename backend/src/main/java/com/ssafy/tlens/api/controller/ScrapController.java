package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.request.ScrapRequestDTO;
import com.ssafy.tlens.api.response.ListAndCntResponseDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.service.ScrapService;
import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.common.model.response.HttpResponseEntity.ResponseResult;
import com.ssafy.tlens.config.auth.PrincipalDetails;
import com.ssafy.tlens.enums.ResponseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.ssafy.tlens.common.model.response.HttpResponseEntity.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/scrap")
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping
    public ResponseResult<?> insert(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Long newsId) throws Exception {
        ScrapRequestDTO scrapRequestDTO = new ScrapRequestDTO(principalDetails.getUser().getUserId(),newsId);
        scrapService.insert(scrapRequestDTO);
        return success();
    }

    @DeleteMapping
    public ResponseResult<?> delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Long newsId) {
        ScrapRequestDTO scrapRequestDTO = new ScrapRequestDTO(principalDetails.getUser().getUserId(),newsId);
        scrapService.delete(scrapRequestDTO);
        return success();
    }

    @GetMapping("/news")
    public ResponseEntity<?> getScrapNewsList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        ListAndCntResponseDTO result = scrapService.getScrapNewsList(principalDetails.getUser().getUserId());

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_INFO_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<?> scrapStatus(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Long newsId) {
        ScrapRequestDTO scrapRequestDTO = new ScrapRequestDTO(principalDetails.getUser().getUserId(),newsId);
        boolean result = scrapService.scrapStatus(scrapRequestDTO);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.USER_MY_INFO_SUCCESS, result), HttpStatus.OK);
    }
}