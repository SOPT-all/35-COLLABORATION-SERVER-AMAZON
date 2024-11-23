package org.sopt.amazonServer.domain.product.controller;

import java.util.List;
import org.sopt.amazonServer.domain.product.model.dto.GetProductResponse;
import org.sopt.amazonServer.domain.product.model.enums.Sort;
import org.sopt.amazonServer.domain.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    ResponseEntity<List<GetProductResponse>> getDiaryList(
            @RequestParam(value = "keyword", required = false) final String keyword,
            @RequestParam(value = "sort", defaultValue = "POPULARITY") final String sort,
            @RequestHeader("memberId") final Long memberId
    ) {
        Sort sortBy = Sort.fromValue(sort.toUpperCase()); // 올바른 정렬 값인지 확인

        return ResponseEntity.ok(productService.fetchProducts(keyword, sortBy, memberId));
    }
}
