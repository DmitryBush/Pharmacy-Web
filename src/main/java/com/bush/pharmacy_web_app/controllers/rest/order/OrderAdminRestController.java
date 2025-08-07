package com.bush.pharmacy_web_app.controllers.rest.order;

import com.bush.pharmacy_web_app.model.dto.orders.OrderStateChangeDto;
import com.bush.pharmacy_web_app.model.entity.order.state.OrderState;
import com.bush.pharmacy_web_app.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/management/orders")
@RequiredArgsConstructor
public class OrderAdminRestController {
    private final OrderService orderService;

    @GetMapping("/{id}/state")
    public OrderState getOrderState(@PathVariable Long id) {
        return orderService.findOrderStateById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
    @PostMapping("/{id}/state")
    public ResponseEntity<Void> requestStateChange(@PathVariable Long id, @RequestBody OrderStateChangeDto dto) {
        try {
            if (orderService.processEvent(id, dto.event()))
                return ResponseEntity.accepted().build();
            else
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
