package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.response.RegionResponseDTO;
import com.ssafy.tlens.api.response.ReporterInfoDTO;
import com.ssafy.tlens.api.service.RegionService;
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
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    @PostMapping("/trend")
    public void insertToRegion(@RequestBody @Valid TrendRequestDTO request) {
        regionService.insertToRegion(request);
    }

    @PatchMapping("/trend")
    public void updateToRegion(@RequestBody @Valid TrendRequestDTO request) {
        regionService.updateToRegion(request);
    }

    @DeleteMapping("/trend")
    public void deleteToRegion(@RequestParam Long id) {
        regionService.deleteToRegion(id);
    }

    @GetMapping
    public ResponseEntity<?> getMainRegion() {
        List<RegionResponseDTO> result = regionService.getMainRegion();

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/news")
    public ResponseEntity<?> getNewsByRegion(@RequestParam String region, @RequestParam int pageNo, @RequestParam int pageSize) {
        List<NewsInfoDTO> result = regionService.getNewsByRegion(region, pageNo, pageSize);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }
}
