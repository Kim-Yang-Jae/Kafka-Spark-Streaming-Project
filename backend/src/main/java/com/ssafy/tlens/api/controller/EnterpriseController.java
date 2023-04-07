package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.EnterpriseDetailDTO;
import com.ssafy.tlens.api.response.MainEnterpriseDTO;
import com.ssafy.tlens.api.service.EnterpriseService;
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
@RequestMapping("/enterprise")
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @PostMapping("/trend")
    public void insertToEnterprise(@RequestBody @Valid TrendRequestDTO request) {
        enterpriseService.insertToEnterprise(request);
    }

    @PatchMapping("/trend")
    public void updateToEnterprise(@RequestBody @Valid TrendRequestDTO request) {
        enterpriseService.updateToEnterprise(request);
    }

    @DeleteMapping("/trend")
    public void deleteToEnterprise(@RequestParam Long id) {
        enterpriseService.deleteToEnterprise(id);
    }

    @GetMapping()
    public ResponseEntity<?> getMainEnterprise() {
        List<MainEnterpriseDTO> result = enterpriseService.getMainEnterprise();

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getEnterpriseDetail(@RequestParam Long enterpriseId) {

        EnterpriseDetailDTO result = enterpriseService.getEnterpriseDetail(enterpriseId);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }
}