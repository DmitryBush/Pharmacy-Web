package com.bush.pharmacy_web_app.controllers.rest.search;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;
import com.bush.pharmacy_web_app.service.medicine.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/medicine")
@RequiredArgsConstructor
public class MedicineRestController {
    private final MedicineService medicineService;

    @GetMapping
    public List<MedicinePreviewReadDto> findMedicineByName(String searchTerm) {
        return medicineService.findByContainingName(searchTerm);
    }
}
