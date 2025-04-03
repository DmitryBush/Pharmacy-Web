package com.bush.pharmacy_web_app.controllers.http;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    @GetMapping
    public String showCart(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        return "user/cart";
    }
}
