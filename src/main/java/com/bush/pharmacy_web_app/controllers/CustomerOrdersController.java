package com.bush.pharmacy_web_app.controllers;

import com.bush.pharmacy_web_app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;


@RequestMapping("api/v1/orders")
@Controller
@RequiredArgsConstructor
public class CustomerOrdersController {
    private final CustomerService customerService;

    @GetMapping
    public String findAllOrders(Model model) {
        var page = new PageImpl<>(
                Objects.requireNonNull(customerService.findById("+79221234567").orElse(null)).orders());

        model.addAttribute("ordersPage", page);
        return "order/order";
    }
}
