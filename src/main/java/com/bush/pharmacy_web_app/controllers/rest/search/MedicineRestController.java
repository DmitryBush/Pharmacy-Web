package com.bush.pharmacy_web_app.controllers.rest.search;

import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;
import com.bush.pharmacy_web_app.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/product")
@RequiredArgsConstructor
public class MedicineRestController {
    private final ProductService productService;

    @GetMapping
    public List<ProductPreviewReadDto> findMedicineByName(String searchTerm) {
        return productService.findByContainingName(searchTerm);
    }
}
