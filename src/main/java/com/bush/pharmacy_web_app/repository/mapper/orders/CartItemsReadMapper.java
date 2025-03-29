package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.CartItemsReadDto;
import com.bush.pharmacy_web_app.repository.dto.orders.MedicineReadDto;
import com.bush.pharmacy_web_app.repository.entity.CartItems;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemsReadMapper implements DtoMapper<CartItems, CartItemsReadDto> {
    private final MedicineReadMapper mapper;
    @Override
    public CartItemsReadDto map(CartItems obj) {
        MedicineReadDto medicineDto = Optional.ofNullable(obj.getMedicine())
                .map(mapper::map)
                .orElse(null);
        return new CartItemsReadDto(medicineDto, obj.getAmount());
    }
}
