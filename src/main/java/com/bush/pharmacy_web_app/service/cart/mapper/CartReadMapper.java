package com.bush.pharmacy_web_app.service.cart.mapper;

import com.bush.pharmacy_web_app.model.dto.cart.CartReadDto;
import com.bush.pharmacy_web_app.model.entity.cart.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CartItemReadMapper.class)
public interface CartReadMapper {
    @Mapping(target = "cartItems", source = "cartItemsList")
    CartReadDto mapToCartReadDto(Cart cart);
}
