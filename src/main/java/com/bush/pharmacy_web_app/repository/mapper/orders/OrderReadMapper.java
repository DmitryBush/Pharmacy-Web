package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.OrderReadDto;
import com.bush.pharmacy_web_app.repository.entity.order.Order;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements DtoMapper<Order, OrderReadDto> {
    private final PharmacyBranchReadMapper branchReadMapper;
    private final OrderItemReadMapper orderReadMapper;
    @Override
    public OrderReadDto map(Order obj) {
        var branch = Optional.ofNullable(obj.getBranch())
                .map(branchReadMapper::map)
                .orElse(null);
        var cart = Optional.ofNullable(obj.getOrderItemList())
                .map(itemList -> itemList.stream().map(orderReadMapper::map).toList())
                .orElseThrow();
        BigDecimal result = cart.stream()
                .map(lamb -> lamb.medicine().price().multiply(BigDecimal.valueOf(lamb.amount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderReadDto(obj.getId(), obj.getStatusOrder(), obj.getDate(),
                branch, cart, result);
    }
}
