package com.ssafy.tlens.api.controller;

import com.ssafy.tlens.api.response.CategoryTrendResponseDTO;
import com.ssafy.tlens.api.service.CategoryTrendService;
import com.ssafy.tlens.common.ResponseDto;
import com.ssafy.tlens.common.model.response.HttpResponseEntity;
import com.ssafy.tlens.enums.ResponseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ssafy.tlens.common.model.response.HttpResponseEntity.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categoryTrend")
public class CategoryTrendController {
    private final CategoryTrendService categoryTrendService;

    @PostMapping("/countKeyword")
    public HttpResponseEntity.ResponseResult<?> countKeyword() {
        categoryTrendService.countKeyword();
        return success();
    }

    @GetMapping("/wordcloud")
    public ResponseEntity<?> makeWordCloud(@RequestParam String category, @RequestParam int count){
        List<CategoryTrendResponseDTO> categoryTrends = categoryTrendService.getCategoryTrends(category,count);
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, categoryTrends), HttpStatus.OK);
    }
}
