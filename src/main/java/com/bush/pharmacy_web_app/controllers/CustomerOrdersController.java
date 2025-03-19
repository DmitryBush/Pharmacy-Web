package com.bush.pharmacy_web_app.controllers;

import com.bush.pharmacy_web_app.repository.dto.orders.OrderReadDto;
import com.bush.pharmacy_web_app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Objects;


@RequestMapping("api/v1/orders")
@RestController
@RequiredArgsConstructor
public class CustomerOrdersController {
    private final CustomerService customerService;
    private final TemplateEngine templateEngine;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String findAllOrders() {
        PageImpl<OrderReadDto> page = new PageImpl<>(
                Objects.requireNonNull(customerService.findById("+79221234567").orElse(null)).orders());

        Context context = new Context();
        context.setVariable("ordersPage", page);
        return templateEngine.process("order/order", context);
    }
}
