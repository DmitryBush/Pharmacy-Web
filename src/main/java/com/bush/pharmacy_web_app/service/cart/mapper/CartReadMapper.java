package com.bush.pharmacy_web_app.service.cart.mapper;

import com.bush.pharmacy_web_app.model.dto.cart.CartReadDto;
import com.bush.pharmacy_web_app.model.entity.cart.Cart;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartReadMapper implements DtoMapper<Cart, CartReadDto> {
    private final CartItemsReadMapper cartItemsReadMapper;
    @Override
    public CartReadDto map(Cart obj) {
        var items = Optional.ofNullable(obj.getCartItemsList())
                .map(list -> list.stream().map(cartItemsReadMapper::map).toList())
                .orElse(null);
        return new CartReadDto(items);
    }
}
