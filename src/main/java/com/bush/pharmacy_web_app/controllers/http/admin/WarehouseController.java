package com.bush.pharmacy_web_app.controllers.http.admin;

import com.bush.pharmacy_web_app.repository.dto.warehouse.StorageItemsReadDto;
import com.bush.pharmacy_web_app.service.PharmacyBranchService;
import com.bush.pharmacy_web_app.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final PharmacyBranchService pharmacyBranchService;
    private final StorageService storageService;

    @GetMapping
    public String getWarehouseList(Model model,
                                   HttpServletRequest httpServletRequest,
                                   @PageableDefault Pageable pageable,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        var branches = pharmacyBranchService.findUserAssignedBranches(userDetails.getUsername());

        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("branches", branches);

        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", httpServletRequest.getRequestURI());
        return "/admin/warehouse";
    }

    @GetMapping("/{id}")
    public String getWarehouseInfo(Model model,
                                   HttpServletRequest httpServletRequest,
                                   @PageableDefault Pageable pageable,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   @PathVariable Long id) {
        var branch = pharmacyBranchService.findByBranchId(id).orElseThrow();
        var items = storageService.findAllItemsByBranchId(id, pageable);

        var countItems = items.stream()
                        .mapToInt(StorageItemsReadDto::amount)
                        .sum();
        var usedSpace = String.format("%.1f",((float) countItems / branch.warehouseLimitations()) * 100.0f);
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("branch", branch);
        model.addAttribute("items", items);
        model.addAttribute("countItems", countItems);
        model.addAttribute("usedSpace", usedSpace);
        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", httpServletRequest.getRequestURI());
        return "/admin/warehouse-info";
    }

    @GetMapping("/{id}/receiving")
    public String showReceivingForm(Model model,
                                    HttpServletRequest httpServletRequest,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    @PathVariable Long id) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("currentUri", httpServletRequest.getRequestURI());
        model.addAttribute("branchId", id);
        model.addAttribute("authorities", authorities);
        return "/admin/warehouse-receipt";
    }

    @GetMapping("/{id}/sale")
    public String showSaleForm(Model model,
                               HttpServletRequest httpServletRequest,
                               @AuthenticationPrincipal UserDetails userDetails,
                               @PathVariable Long id) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("currentUri", httpServletRequest.getRequestURI());
        model.addAttribute("branchId", id);
        model.addAttribute("authorities", authorities);
        return "/admin/warehouse-sale";
    }
}
