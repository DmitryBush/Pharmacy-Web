package com.bush.pharmacy_web_app.controllers.http.admin;

import com.bush.pharmacy_web_app.repository.dto.warehouse.StorageItemsReadDto;
import com.bush.pharmacy_web_app.service.PharmacyBranchService;
import com.bush.pharmacy_web_app.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final PharmacyBranchService pharmacyBranchService;
    private final StorageService storageService;

    @GetMapping
    public String getWarehouseInfo(Model model,
                                   HttpServletRequest httpServletRequest,
                                   @PageableDefault
                                   Pageable pageable) {
        var branch = pharmacyBranchService.findByBranchId(1L).orElseThrow();
        var items = storageService.findAllItemsByBranchId(1L, pageable);

        var countItems = items.stream()
                        .mapToInt(StorageItemsReadDto::amount)
                        .sum();
        var usedSpace = String.format("%.1f",((float) countItems / branch.warehouseLimitations()) * 100.0f);

        model.addAttribute("branch", branch);
        model.addAttribute("items", items);
        model.addAttribute("countItems", countItems);
        model.addAttribute("usedSpace", usedSpace);
        model.addAttribute("currentUri", httpServletRequest.getRequestURI());
        return "/admin/warehouse";
    }

    @GetMapping("/receiving")
    public String showReceivingForm(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("currentUri", httpServletRequest.getRequestURI());
        return "/admin/warehouse-receipt";
    }
}
