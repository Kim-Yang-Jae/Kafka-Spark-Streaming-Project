package com.ssafy.tlens.api.controller;


import com.ssafy.tlens.api.request.TrendRequestDTO;
import com.ssafy.tlens.api.response.CountNewsByCategoryDTO;
import com.ssafy.tlens.api.response.MainCategoryDTO;
import com.ssafy.tlens.api.response.NewsInfoDTO;
import com.ssafy.tlens.api.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/trend")
    public void insertToCategory(@RequestBody @Valid TrendRequestDTO request) {
        categoryService.insertToCategory(request);
    }

    @PatchMapping("/trend")
    public void updateToCategory(@RequestBody @Valid TrendRequestDTO request) {
        categoryService.updateToCategory(request);
    }

    @DeleteMapping("/trend")
    public void deleteToCategory(@RequestParam Long id) {
        categoryService.deleteToCategory(id);
    }

    @GetMapping
    public ResponseEntity<?> getMainCategory() {
        List<MainCategoryDTO> result = categoryService.getMainCategory();

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }
    
    @GetMapping("/news")
    public ResponseEntity<?> getNewsByCategory(@RequestParam String category, @RequestParam int pageNo, @RequestParam int pageSize) {
        List<NewsInfoDTO> result = categoryService.getNewsByCategory(category, pageNo, pageSize);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<?> countNewsByCategory(@RequestParam String category) {
        CountNewsByCategoryDTO result = categoryService.countNewsByCategory(category);

        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.PRODUCT_CATEGORY_SUCCESS, result), HttpStatus.OK);
    }

}
