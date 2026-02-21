package com.bush.search.controller;

import com.bush.search.domain.dto.ProductPreviewDto;
import com.bush.search.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/search/products")
@RequiredArgsConstructor
public class ProductSearchRestController {
    private final ProductService productService;

    @GetMapping("/name")
    public ResponseEntity<List<ProductPreviewDto>> findProductsByName(String productName) {
        return ResponseEntity.ok(productService.findProductsByName(productName));
    }
}
