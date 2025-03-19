package com.bush.pharmacy_web_app.controllers;

import com.bush.pharmacy_web_app.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {
    private final MedicineService service;

    @GetMapping
    public String findAllMedicines(Model model) {
        model.addAttribute("medicines", service.findAll());
        return "catalog/catalog";
    }
}
