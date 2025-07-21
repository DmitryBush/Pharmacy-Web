package com.bush.pharmacy_web_app.controllers.http.admin;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;
import com.bush.pharmacy_web_app.service.OrderService;
import com.bush.pharmacy_web_app.service.PharmacyBranchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;
    private final PharmacyBranchService pharmacyBranchService;

    @GetMapping
    public String getWarehouseList(Model model,
                                   HttpServletRequest httpServletRequest,
                                   @PageableDefault Pageable pageable,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        var branches = pharmacyBranchService.findUserAssignedBranches(userDetails.getUsername());

        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("branches", branches);

        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", httpServletRequest.getRequestURI());
        return "/admin/order";
    }

    @GetMapping("branch/{branchId}")
    public String getOrdersList(Model model,
                            HttpServletRequest httpRequest,
                            @PageableDefault(size = 15) Pageable pageable,
                            @PathVariable Long branchId,
                            @AuthenticationPrincipal UserDetails userDetails) {
        var orders = orderService.findAllOrdersByBranch(branchId, pageable);
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("orders", orders);
        model.addAttribute("OrderStatus", OrderState.class);
        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", httpRequest.getRequestURI());
        return "/admin/order-list";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model model, HttpServletRequest httpRequest,
                           @AuthenticationPrincipal UserDetails userDetails) {
        var order = orderService.findOrderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        model.addAttribute("order", order);
        model.addAttribute("OrderStatus", OrderState.class);
        model.addAttribute("authorities", authorities);
        model.addAttribute("currentUri", httpRequest.getRequestURI());
        return "/admin/order-management";
    }
}
