package org.sopt.amazonServer.domain.product.controller;


import org.sopt.amazonServer.domain.product.model.dto.GetProductRequest;
import org.sopt.amazonServer.domain.product.model.enums.Sort;
import org.sopt.amazonServer.domain.product.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    ResponseEntity<List<GetProductRequest>> getDiaryList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "POPULARITY") String sort,
            @RequestHeader("memberId") Long memberId
    ) {
        // TODO: 코드 스타일 정리하기
       Sort sortBy = Sort.fromValue(sort);

        return ResponseEntity.ok(productService.fetchProducts(keyword,sortBy,memberId));
    }


}
