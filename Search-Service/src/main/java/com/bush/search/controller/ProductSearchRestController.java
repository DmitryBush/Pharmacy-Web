package com.bush.search.controller;

import com.bush.search.domain.dto.ProductFilter;
import com.bush.search.domain.dto.ProductPreviewDto;
import com.bush.search.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/products")
@RequiredArgsConstructor
public class ProductSearchRestController {
    private final ProductService productService;

    @GetMapping("/name")
    public ResponseEntity<List<ProductPreviewDto>> findProductsByName(String productName) {
        return ResponseEntity.ok(productService.findProductsByName(productName));
    }

    @GetMapping("/filter")
    public ResponseEntity<PagedModel<EntityModel<ProductPreviewDto>>> findProductByFilter(Pageable pageable,
                                                                                          ProductFilter filter,
                                                                                          PagedResourcesAssembler<ProductPreviewDto> assembler) {
        return ResponseEntity.ok(assembler.toModel(productService.findProductsByFilter(filter, pageable)));
    }
}
