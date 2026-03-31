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
@RequestMapping("/admin/staff")
public class StaffManagementController {

    @GetMapping
    public String getStaff(Model model, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/user/staff-management";
    }

    @GetMapping("/role")
    public String getRolePage(Model model, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/user/role-management";
    }
}
