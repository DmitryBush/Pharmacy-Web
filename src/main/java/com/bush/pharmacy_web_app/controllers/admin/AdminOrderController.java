package com.bush.pharmacy_web_app.controllers.admin;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;
import com.bush.pharmacy_web_app.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;

    @GetMapping
    public String getOrders(Model model,
                            HttpServletRequest httpRequest,
                            @PageableDefault(size = 15) Pageable pageable) {
        var orders = orderService.findAllOrdersByBranch(1L, pageable);

        model.addAttribute("orders", orders);
        model.addAttribute("OrderStatus", OrderStatus.class);
        model.addAttribute("currentUri", httpRequest.getRequestURI());
        return "/admin/order";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model model, HttpServletRequest httpRequest) {
        var order = orderService.findOrderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        model.addAttribute("order", order);
        model.addAttribute("OrderStatus", OrderStatus.class);
        model.addAttribute("currentUri", httpRequest.getRequestURI());
        return "/admin/order-management";
    }
}
