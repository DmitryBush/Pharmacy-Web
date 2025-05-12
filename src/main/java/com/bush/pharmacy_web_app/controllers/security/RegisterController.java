package com.bush.pharmacy_web_app.controllers.security;

import com.bush.pharmacy_web_app.repository.dto.user.CustomerCreateDto;
import com.bush.pharmacy_web_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService service;

    @GetMapping
    public String register() {
        return "user/register/register";
    }
    @PostMapping
    public String register(@ModelAttribute("customer")@Validated CustomerCreateDto createDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "user/register/register";

        try {
            service.create(createDto);
            return "redirect:/login";
        } catch (Exception e) {
            bindingResult.rejectValue("username", "error.user", e.getMessage());
            return "register";
        }
    }
}
