package com.bush.pharmacy_web_app.controllers.rest.admin;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeUpdateDto;
import com.bush.pharmacy_web_app.service.medicine.MedicineTypeService;
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
    private final MedicineTypeService medicineTypeService;

    @GetMapping
    public ResponseEntity<List<MedicineTypeDto>> getCategoriesList(String parent) {
        return ResponseEntity.ok(medicineTypeService.findAllTypesByParent(parent));
    }

    @PostMapping
    public ResponseEntity<MedicineTypeDto> createCategory(@RequestBody @Validated MedicineTypeDto createDto) {
        return ResponseEntity.ok(medicineTypeService.createDto(createDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MedicineTypeDto> updateCategory(@PathVariable Integer id,
                                                          @RequestBody MedicineTypeUpdateDto updateDto) {
        return ResponseEntity.ok(medicineTypeService.updatePartlyType(id, updateDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Integer id) {
        if (medicineTypeService.deleteType(id))
            return ResponseEntity.accepted().body(true);
        return ResponseEntity.badRequest().body(false);
    }
}
