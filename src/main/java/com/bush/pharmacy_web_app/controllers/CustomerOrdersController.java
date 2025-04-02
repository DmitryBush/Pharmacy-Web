package com.bush.pharmacy_web_app.controllers;

import com.bush.pharmacy_web_app.repository.dto.CustomerReadDto;
import com.bush.pharmacy_web_app.repository.dto.OrderReadDto;
import com.bush.pharmacy_web_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Objects;

//@Controller
@RequestMapping("api/v1/orders")
@RestController
@RequiredArgsConstructor
public class CustomerOrdersController {
    private final UserService userService;
    private final TemplateEngine templateEngine;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String findAll(@PageableDefault Pageable pageable, @AuthenticationPrincipal UserDetails userDetails) {
        PageImpl<OrderReadDto> page = new PageImpl<>(
                Objects.requireNonNull(userService.findById(userDetails.getUsername()).orElse(null)).orders());

        Context context = new Context();
        context.setVariable("ordersPage", page);
        return templateEngine.process("order/order", context);
    }
}
