package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.OrderReadDto;
import com.bush.pharmacy_web_app.repository.entity.Order;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements DtoMapper<Order, OrderReadDto> {
    private final PharmacyBranchReadMapper branchReadMapper;
    private final CartReadMapper cartReadMapper;
    @Override
    public OrderReadDto map(Order obj) {
        var branch = Optional.ofNullable(obj.getBranch())
                .map(branchReadMapper::map)
                .orElse(null);
        var cart = Optional.ofNullable(obj.getCart())
                .map(cartReadMapper::map)
                .orElseThrow();
        BigDecimal result = cart.cartItems().stream()
                .map(lamb -> lamb.medicine().price().multiply(BigDecimal.valueOf(lamb.amount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderReadDto(obj.getId(), obj.getStatusOrder(), obj.getDate(),
                branch, cart, result);
    }
}
