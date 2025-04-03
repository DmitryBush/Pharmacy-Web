package com.bush.pharmacy_web_app.controllers.http;

import com.bush.pharmacy_web_app.repository.dto.orders.CartReadDto;
import com.bush.pharmacy_web_app.service.CartService;
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

        if (userDetails != null)
            model.addAttribute("cartItems", cartService.findById(1L));
        else
            model.addAttribute("cartItems", null);
        return "user/cart";
    }
}
