package com.bush.pharmacy_web_app.controllers.http.customer;

import com.bush.pharmacy_web_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;


@RequestMapping("/orders")
@Controller
@RequiredArgsConstructor
public class CustomerOrdersController {
    private final UserService userService;

    @GetMapping
    public String findAllOrders(Model model,
                                @AuthenticationPrincipal UserDetails userDetails,
                                @PageableDefault Pageable pageable) {
        var page = new PageImpl<>(
                Objects.requireNonNull(userService.findById(userDetails.getUsername()).orElse(null)).orders());

        model.addAttribute("ordersPage", page);
        return "order/order";
    }
}
