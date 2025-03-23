package com.bush.pharmacy_web_app.controllers.rest;

import com.bush.pharmacy_web_app.repository.dto.orders.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogRestController {
    private final MedicineService service;

    @GetMapping("/{id}")
    public ResponseEntity<List<PharmacyBranchReadDto>> getAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(service.findBranchesMedicineLocated(id));
    }
}
