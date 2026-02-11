package com.bush.pharmacy_web_app.controllers.http;

import com.bush.pharmacy_web_app.repository.medicine.filter.MedicineFilter;
import com.bush.pharmacy_web_app.service.medicine.MedicineService;
import com.bush.pharmacy_web_app.service.medicine.MedicineTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {
    private final MedicineTypeService typeService;
    private final MedicineService service;

    @GetMapping
    public String showAllProducts() {
        return "catalog/catalog";
    }

    @GetMapping("/{id}")
    public String getProduct(Model model,
                             @PathVariable Long id) {
        var product = service.findMedicineById(id).orElseThrow();

        model.addAttribute("product", product);
        return "catalog/product";
    }
}
