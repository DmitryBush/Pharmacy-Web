package com.bush.pharmacy_web_app.controllers.rest.search;

import com.bush.pharmacy_web_app.repository.dto.orders.MedicineReadDto;
import com.bush.pharmacy_web_app.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search/medicine")
@RequiredArgsConstructor
public class MedicineRestController {
    private final MedicineService medicineService;

    @GetMapping
    public List<MedicineReadDto> findMedicineByName(String name) {
        return medicineService.findByContainingName(name);
    }
}
