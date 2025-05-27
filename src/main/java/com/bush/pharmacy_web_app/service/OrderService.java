package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.OrderRepository;
import com.bush.pharmacy_web_app.repository.dto.orders.AdminOrderDto;
import com.bush.pharmacy_web_app.repository.mapper.orders.AdminOrderReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final AdminOrderReadMapper adminOrderReadMapper;

    public Page<AdminOrderDto> findAllOrdersByBranch(Long id, Pageable pageable) {
        return orderRepository.findByBranchId(id, pageable)
                .map(adminOrderReadMapper::map);
    }

    public Optional<AdminOrderDto> findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(adminOrderReadMapper::map);
    }
}
