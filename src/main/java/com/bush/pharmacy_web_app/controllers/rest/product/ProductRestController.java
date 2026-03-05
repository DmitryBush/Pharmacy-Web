package com.bush.pharmacy_web_app.controllers.rest.product;

import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;
import com.bush.pharmacy_web_app.repository.product.filter.ProductFilter;
import com.bush.pharmacy_web_app.service.branch.TransactionService;
import com.bush.pharmacy_web_app.service.product.ProductService;
import com.bush.pharmacy_web_app.service.product.dailyfeatured.DailyFeaturedProductService;
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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {
    private final DailyFeaturedProductService dailyFeaturedProductService;
    private final TransactionService transactionService;
    private final ProductService productService;

    @GetMapping("/daily-products")
    public List<ProductPreviewReadDto> getDailyProducts() {
        return dailyFeaturedProductService.findDailyMedicine();
    }

    @GetMapping("/best-sellers")
    public List<ProductPreviewReadDto> getBestSellingProducts() {
        return transactionService.findBestSellingProducts();
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ProductPreviewReadDto>>> getProductsByFilter(Pageable pageable,
                                                                                              ProductFilter filter,
                                                                                              PagedResourcesAssembler<ProductPreviewReadDto> assembler) {
        return ResponseEntity.ok(assembler.toModel(productService.findAllPreviews(filter, pageable)));
    }
}
