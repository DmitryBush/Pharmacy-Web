package com.bush.pharmacy_web_app.repository.mapper.cart;

import com.bush.pharmacy_web_app.repository.UserRepository;
import com.bush.pharmacy_web_app.repository.dto.cart.CartCreateDto;
import com.bush.pharmacy_web_app.repository.entity.cart.Cart;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartCreateMapper implements DtoMapper<CartCreateDto, Cart> {
    private final UserRepository userRepository;
    private final CartItemsCreateMapper cartItemsCreateMapper;
    @Override
    public Cart map(CartCreateDto obj) {
        return copyObj(obj, new Cart());
    }

    @Override
    public Cart map(CartCreateDto fromObj, Cart toObj) {
        return DtoMapper.super.map(fromObj, toObj);
    }

    private Cart copyObj(CartCreateDto fromObj, Cart toObj) {
        var user = userRepository.findById(fromObj.user().mobilePhone())
                .orElseThrow();
        var cartItems = Optional.ofNullable(fromObj.cartItemsList())
                        .map(item -> item.stream().map(cartItemsCreateMapper::map).toList())
                        .orElse(Collections.emptyList());

        toObj.setUser(user);
        toObj.setCartItemsList(cartItems);
        return toObj;
    }
}
