package com.bush.pharmacy_web_app.controllers.http.admin;

import com.bush.pharmacy_web_app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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

        model.addAttribute("user", user);
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/mainDashboard";
    }
}
