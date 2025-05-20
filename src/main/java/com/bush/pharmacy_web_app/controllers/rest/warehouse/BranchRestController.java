package com.bush.pharmacy_web_app.controllers.rest.warehouse;

import com.bush.pharmacy_web_app.repository.dto.warehouse.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.service.PharmacyBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/branches")
@RequiredArgsConstructor
public class BranchRestController {
    private final PharmacyBranchService pharmacyBranchService;

    @GetMapping("/{id}")
    public ResponseEntity<PharmacyBranchReadDto> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(pharmacyBranchService.findByBranchId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/products/{medicineId}")
    public ResponseEntity<List<PharmacyBranchReadDto>> getBranchesWithProduct(@PathVariable Long medicineId) {
        return ResponseEntity.ok(pharmacyBranchService.findBranchesWithMedicineLocated(medicineId));
    }
}
