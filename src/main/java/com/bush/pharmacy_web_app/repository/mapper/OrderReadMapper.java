package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.OrderReadDto;
import com.bush.pharmacy_web_app.repository.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements DtoMapper<Order, OrderReadDto> {
    private final PharmacyBranchReadMapper branchReadMapper;
    private final CartItemsReadMapper cartItemsReadMapper;
    @Override
    public OrderReadDto map(Order obj) {
        var branch = Optional.ofNullable(obj.getBranch())
                .map(branchReadMapper::map)
                .orElse(null);
        var cart = Optional.ofNullable(obj.getCartItems())
                .map(list -> list.stream()
                        .map(cartItemsReadMapper::map)
                        .toList())
                .orElse(Collections.emptyList());

        return new OrderReadDto(obj.getId(), obj.getStatusOrder(), obj.getDate(), branch, cart);
    }
}
