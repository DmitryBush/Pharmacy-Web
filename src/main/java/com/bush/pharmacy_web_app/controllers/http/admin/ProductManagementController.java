package com.bush.pharmacy_web_app.controllers.http.admin;

import com.bush.pharmacy_web_app.repository.product.filter.ProductFilter;
import com.bush.pharmacy_web_app.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductManagementController {
    private final ProductService productService;
    @GetMapping
    public String showProductList(Model model,
                                  ProductFilter productFilter,
                                  @PageableDefault(size = 30, sort = "price", direction = Sort.Direction.ASC) Pageable pageable,
                                  HttpServletRequest request,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        var page = productService.findAllPreviews(productFilter, pageable);
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("products", page);
        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/product-management";
    }

    @GetMapping("/creation")
    public String getCreationForm(Model model, HttpServletRequest request,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "/admin/creating-product";
    }

    @GetMapping("/{id}")
    public String getProduct(Model model, @PathVariable Long id, HttpServletRequest request,
                             @AuthenticationPrincipal UserDetails userDetails) {
        var product = productService.findAdminDtoById(id)
                        .orElseThrow();
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("product", product);
        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/editing-product";
    }
}
