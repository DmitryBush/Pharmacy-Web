package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.OrderRepository;
import com.bush.pharmacy_web_app.repository.entity.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<Order> findAllOrdersByBranch(Long id, Pageable pageable) {
        return orderRepository.findByBranchId(id, pageable);
    }
}
