package com.bush.pharmacy_web_app.controllers.http.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/news")
public class NewsManagementController {

    @GetMapping
    public String getNewsManagementPage(Model model,
                                        HttpServletRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "/admin/news-management";
    }

    @GetMapping("/creation")
    public String getCreationForm(Model model,
                                  HttpServletRequest request,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "/admin/editing-news";
    }
}
