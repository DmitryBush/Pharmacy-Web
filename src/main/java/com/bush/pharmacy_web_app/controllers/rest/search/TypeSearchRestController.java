package com.bush.pharmacy_web_app.controllers.rest.search;

import com.bush.pharmacy_web_app.model.dto.product.ProductTypeDto;
import com.bush.pharmacy_web_app.service.product.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/type")
public class TypeSearchRestController {
    private final ProductTypeService typeService;

    @GetMapping
    public ResponseEntity<List<ProductTypeDto>> findTypes(String searchTerm) {
        return ResponseEntity.ok(typeService.searchTypesByName(searchTerm));
    }

    @GetMapping("/parent")
    public ResponseEntity<List<ProductTypeDto>> findParentTypes(String searchTerm) {
        return ResponseEntity.ok(typeService.searchParentTypesByName(searchTerm));
    }

    @GetMapping("/by-name")
    public ResponseEntity<ProductTypeDto> findMedicineType(String type) {
        return ResponseEntity.ok(typeService.findByTypeName(type));
    }
}
