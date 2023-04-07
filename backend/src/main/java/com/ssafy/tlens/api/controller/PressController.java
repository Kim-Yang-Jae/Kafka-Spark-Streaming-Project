package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.MainPressDTO;
import com.ssafy.tlens.api.service.PressService;
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
@RequestMapping("/press")
public class PressController {

    private final PressService pressService;

    @PostMapping("/trend")
    public void insertToPress(@RequestBody @Valid TrendRequestDTO request) {
        pressService.insertToPress(request);
    }

    @PatchMapping("/trend")
    public void updateToPress(@RequestBody @Valid TrendRequestDTO request) {
        pressService.updateToPress(request);
    }

    @DeleteMapping("/trend")
    public void deleteToPress(@RequestParam Long id) {
        pressService.deleteToPress(id);
    }

    @GetMapping()
    public ResponseEntity<?> getMainPress() {
        List<MainPressDTO> result = pressService.getMainPress();
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }

    @PostMapping("/setting")
    public void settingPress(){
        pressService.settingPress();
    }
}
