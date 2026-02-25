package com.bush.search.controller;

import com.bush.search.controller.response.FilteredResponse;
import com.bush.search.domain.dto.ProductFilter;
import com.bush.search.domain.dto.ProductPreviewDto;
import com.bush.search.repository.filter.ProductAggregation;
import com.bush.search.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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
    public ResponseEntity<FilteredResponse<ProductPreviewDto, ProductAggregation>> findProductByFilter(Pageable pageable,
                                                                                                       @Validated ProductFilter filter,
                                                                                                       PagedResourcesAssembler<ProductPreviewDto> assembler) {
        var filteredTuple = productService.findProductsByFilter(filter, pageable);
        var assembled = assembler.toModel(filteredTuple.filtrationResult());
        return ResponseEntity.ok(new FilteredResponse<>(assembled, filteredTuple.aggregation()));
    }
}
