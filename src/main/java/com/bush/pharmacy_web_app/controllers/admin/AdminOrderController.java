package com.bush.pharmacy_web_app.controllers.admin;

import com.bush.pharmacy_web_app.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("currentUri", httpRequest.getRequestURI());
        return "/admin/order-controller";
    }
}
