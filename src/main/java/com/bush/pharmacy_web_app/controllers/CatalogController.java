package com.bush.pharmacy_web_app.controllers;

import com.bush.pharmacy_web_app.repository.dto.PageResponse;
import com.bush.pharmacy_web_app.repository.filter.MedicineFilter;
import com.bush.pharmacy_web_app.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {
    private final MedicineService service;

    @GetMapping
    public String findAllMedicines(Model model,
                                   MedicineFilter filter,
                                   @PageableDefault(size = 15, sort = "price", direction = Sort.Direction.ASC)
                                       Pageable pageable) {
        var page = service.findAll(filter, pageable);

        model.addAttribute("types", service.findAllTypes());
        model.addAttribute("manufacturers", service.findAllManufacturers());
        model.addAttribute("medicines", page);
        return "catalog/catalog";
    }
}
