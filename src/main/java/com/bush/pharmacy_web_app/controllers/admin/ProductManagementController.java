package com.bush.pharmacy_web_app.controllers.admin;

import com.bush.pharmacy_web_app.repository.filter.MedicineFilter;
import com.bush.pharmacy_web_app.service.MedicineService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductManagementController {
    private final MedicineService medicineService;
    @GetMapping
    public String showProductList(Model model,
                                  MedicineFilter medicineFilter,
                                  @PageableDefault(size = 15, sort = "price", direction = Sort.Direction.ASC) Pageable pageable,
                                  HttpServletRequest request) {
        var page = medicineService.findAllPreviews(medicineFilter, pageable);

        model.addAttribute("products", page);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/productManagement";
    }

    @GetMapping("/{id}")
    public String getProduct(Model model, @PathVariable Long id, HttpServletRequest request) {
        var product = medicineService.findAdminDtoById(id)
                        .orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/editingProduct";
    }
}
