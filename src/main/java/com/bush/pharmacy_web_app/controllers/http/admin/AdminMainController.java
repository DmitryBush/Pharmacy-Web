package com.bush.pharmacy_web_app.controllers.http.admin;

import com.bush.pharmacy_web_app.service.user.UserService;
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
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class AdminMainController {
    private final UserService userService;
    @GetMapping
    public String showMainPage(Model model, HttpServletRequest request,
                               @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findById(userDetails.getUsername())
                .orElseThrow();
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("user", user);
        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/main-dashboard";
    }
}
