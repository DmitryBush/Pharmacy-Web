package com.bush.pharmacy_web_app.service.order.mapper;

import com.bush.pharmacy_web_app.model.dto.orders.OrderReadDto;
import com.bush.pharmacy_web_app.model.entity.order.Order;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements DtoMapper<Order, OrderReadDto> {
    private final PharmacyBranchReadMapper branchReadMapper;
    private final OrderItemReadMapper orderReadMapper;
    private final OrderStatusReadMapper orderStatusReadMapper;
    @Override
    public OrderReadDto map(Order obj) {
        var status = Optional.ofNullable(obj.getStatus())
                .map(orderStatusReadMapper::map)
                .orElseThrow();
        var branch = Optional.ofNullable(obj.getBranch())
                .map(branchReadMapper::map)
                .orElse(null);
        var cart = Optional.ofNullable(obj.getOrderItemList())
                .map(itemList -> itemList.stream().map(orderReadMapper::map).toList())
                .orElseThrow();
        BigDecimal result = cart.stream()
                .map(lamb -> lamb.medicine().price().multiply(BigDecimal.valueOf(lamb.amount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderReadDto(obj.getId(), status, obj.getDate(),
                branch, cart, result);
    }
}
