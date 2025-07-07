package com.bush.pharmacy_web_app.controllers.rest.order;

import com.bush.pharmacy_web_app.repository.dto.orders.OrderStateChangeDto;
import com.bush.pharmacy_web_app.repository.entity.order.state.OrderEvent;
import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;
import com.bush.pharmacy_web_app.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;

    @GetMapping("/{id}/state")
    public OrderState getOrderState(@PathVariable Long id) {
        return orderService.findOrderStateById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

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
