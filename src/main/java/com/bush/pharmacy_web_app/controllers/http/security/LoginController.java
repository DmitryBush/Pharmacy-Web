package com.bush.pharmacy_web_app.controllers.http.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getSite() {
        return "user/login/login";
    }
}
