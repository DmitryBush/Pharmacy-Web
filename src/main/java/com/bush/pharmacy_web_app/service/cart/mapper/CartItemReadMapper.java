package com.bush.pharmacy_web_app.service.cart.mapper;

import com.bush.pharmacy_web_app.model.dto.cart.CartItemReadDto;
import com.bush.pharmacy_web_app.model.entity.cart.CartItems;
import com.bush.pharmacy_web_app.service.product.mapper.ProductReadMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProductReadMapper.class)
public interface CartItemReadMapper {
    @Mapping(target = "medicine", source = "id.product")
    CartItemReadDto mapToCartItemReadDto(CartItems cartItems);
}
