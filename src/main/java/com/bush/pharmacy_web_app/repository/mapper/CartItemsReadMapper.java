package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.CartItemsReadDto;
import com.bush.pharmacy_web_app.repository.dto.MedicineOrderDto;
import com.bush.pharmacy_web_app.repository.entity.CartItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemsReadMapper implements DtoMapper<CartItems, CartItemsReadDto> {
    private final MedicineOrderMapper mapper;
    @Override
    public CartItemsReadDto map(CartItems obj) {
        MedicineOrderDto medicineDto = Optional.ofNullable(obj.getMedicine())
                .map(mapper::map)
                .orElse(null);
        return new CartItemsReadDto(medicineDto, obj.getAmount());
    }
}
