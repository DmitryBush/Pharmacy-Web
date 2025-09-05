package com.bush.pharmacy_web_app.controllers.http.customer;

import com.bush.pharmacy_web_app.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public String showCart(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var cart = cartService.findById(1L)
                .orElse(null);
        if (userDetails != null)
            model.addAttribute("cart", cart);
        else
            model.addAttribute("cart", null);
        return "user/cart";
    }
}
