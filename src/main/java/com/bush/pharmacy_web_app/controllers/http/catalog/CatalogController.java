package com.bush.pharmacy_web_app.controllers.http.catalog;

import com.bush.pharmacy_web_app.service.product.ProductService;
import com.bush.pharmacy_web_app.service.product.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {
    private final ProductTypeService typeService;
    private final ProductService service;

    @GetMapping
    public String showAllProducts() {
        return "catalog/catalog";
    }

    @GetMapping("/{typeSlug}")
    public String showProductsByType(@PathVariable String typeSlug) {
        return "catalog/catalog";
    }
}
