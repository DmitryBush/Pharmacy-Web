package com.bush.pharmacy_web_app.controllers.rest.search;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.service.medicine.MedicineTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/type")
public class TypeSearchRestController {
    private final MedicineTypeService typeService;

    @GetMapping
    public ResponseEntity<List<MedicineTypeDto>> findTypes(String searchTerm) {
        return ResponseEntity.ok(typeService.searchTypesByName(searchTerm));
    }

    @GetMapping("/parent")
    public ResponseEntity<List<MedicineTypeDto>> findParentTypes(String searchTerm) {
        return ResponseEntity.ok(typeService.searchParentTypesByName(searchTerm));
    }

    @GetMapping("/by-name")
    public ResponseEntity<MedicineTypeDto> findMedicineType(String type) {
        return ResponseEntity.ok(typeService.findByType(type)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
