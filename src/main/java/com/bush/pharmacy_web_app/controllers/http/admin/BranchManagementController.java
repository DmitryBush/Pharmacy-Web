package com.bush.pharmacy_web_app.controllers.http.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/branch")
public class BranchManagementController {

    @GetMapping
    public String getBranchManagementPagesList(Model model, HttpServletRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/branch/branch-management";
    }

    @GetMapping("/create")
    public String getBranchCreationPage(Model model, HttpServletRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/branch/branch-editing";
    }

    @GetMapping("/{id}")
    public String getBranchInfoPage(Model model, HttpServletRequest request,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/branch/branch-info";
    }
}
