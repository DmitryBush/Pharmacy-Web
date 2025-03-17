package com.bush.pharmacy_web_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class LoginController {
    private final TemplateEngine templateEngine;

    @GetMapping
    public String getSite() {
        Context context = new Context();

        return templateEngine.process("user/login/login", context);
    }

}
