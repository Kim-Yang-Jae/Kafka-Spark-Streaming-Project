package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.ReporterInfoDTO;
import com.ssafy.tlens.api.response.SubscribeInfoResponseDTO;
import com.ssafy.tlens.api.response.WordCountDTO;
import com.ssafy.tlens.api.service.ReporterService;
import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.enums.ResponseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reporter")
public class ReporterController {

    private final ReporterService reporterService;

    @PostMapping("/trend")
    public void insertToReporter(@RequestBody @Valid TrendRequestDTO request) {
        reporterService.insertToReporter(request);
    }

    @PatchMapping("/trend")
    public void updateToReporter(@RequestBody @Valid TrendRequestDTO request) {
        reporterService.updateToReporter(request);
    }

    @DeleteMapping("/trend")
    public void deleteToReporter(@RequestParam Long id) {
        reporterService.deleteToReporter(id);
    }

    @GetMapping("/press")
    public ResponseEntity<?> getReportersByPress(@RequestParam String press, @RequestParam int pageNo, @RequestParam int pageSize) {
        List<ReporterInfoDTO> result = reporterService.getReportersByPressOffset(press, pageNo, pageSize);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/news")
    public ResponseEntity<?> getNewsByReporter(@RequestParam String reporter, @RequestParam int pageNo, @RequestParam int pageSize) {
        List<NewsInfoDTO> result = reporterService.getNewsByReporter(reporter, pageNo, pageSize);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/category/count")
    public ResponseEntity<?> getCategoryByReporterNews(@RequestParam String reporter) {
        List<WordCountDTO> result = reporterService.getCategoryCountByReporterNews(reporter);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/subscribe")
    public ResponseEntity<?> getSubscribeInfoByReporter(@RequestParam Long reporterId) {
        SubscribeInfoResponseDTO result = reporterService.getSubscribeInfoByReporter(reporterId);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }
}
