package com.bush.pharmacy_web_app.service.cart.mapper;

import com.bush.pharmacy_web_app.model.dto.cart.CartItemReadDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;
import com.bush.pharmacy_web_app.model.entity.cart.CartItems;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.product.mapper.MedicinePreviewReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemsReadMapper implements DtoMapper<CartItems, CartItemReadDto> {
    private final MedicinePreviewReadMapper mapper;
    @Override
    public CartItemReadDto map(CartItems obj) {
        ProductPreviewReadDto medicineDto = Optional.ofNullable(obj.getProduct())
                .map(mapper::map)
                .orElse(null);
        return new CartItemReadDto(medicineDto, obj.getAmount());
    }
}
