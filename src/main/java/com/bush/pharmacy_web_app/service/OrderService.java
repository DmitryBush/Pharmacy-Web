package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.OrderRepository;
import com.bush.pharmacy_web_app.repository.dto.orders.OrderReadDto;
import com.bush.pharmacy_web_app.repository.mapper.orders.OrderReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final OrderReadMapper orderReadMapper;

    public Page<OrderReadDto> findAllOrdersByBranch(Long id, Pageable pageable) {
        return orderRepository.findByBranchId(id, pageable)
                .map(orderReadMapper::map);
    }
}
