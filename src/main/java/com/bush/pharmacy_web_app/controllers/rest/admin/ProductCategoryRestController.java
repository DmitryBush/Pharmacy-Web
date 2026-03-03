package com.bush.pharmacy_web_app.controllers.rest.admin;

import com.bush.pharmacy_web_app.model.dto.product.ProductTypeDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductTypeUpdateDto;
import com.bush.pharmacy_web_app.service.product.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class ProductCategoryRestController {
    private final ProductTypeService productTypeService;

    @GetMapping
    public ResponseEntity<List<ProductTypeDto>> getCategoriesList(String parent) {
        return ResponseEntity.ok(productTypeService.findAllTypesByParent(parent));
    }

    @PostMapping
    public ResponseEntity<ProductTypeDto> createCategory(@RequestBody @Validated ProductTypeDto createDto) {
        return ResponseEntity.ok(productTypeService.createDto(createDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductTypeDto> updateCategory(@PathVariable Integer id,
                                                         @RequestBody ProductTypeUpdateDto updateDto) {
        return ResponseEntity.ok(productTypeService.updatePartlyType(id, updateDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Integer id) {
        if (productTypeService.deleteType(id))
            return ResponseEntity.accepted().body(true);
        return ResponseEntity.badRequest().body(false);
    }
}
