package com.bush.pharmacy_web_app.controllers.rest.product;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.service.medicine.MedicineTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class ProductCategoryRestController {
    private final MedicineTypeService medicineTypeService;

    @GetMapping
    public ResponseEntity<List<MedicineTypeDto>> getCategoriesList(String parent) {
        return ResponseEntity.ok(medicineTypeService.findAllTypesByParent(parent));
    }
}
