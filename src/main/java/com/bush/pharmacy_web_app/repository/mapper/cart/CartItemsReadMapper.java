package com.bush.pharmacy_web_app.repository.mapper.cart;

import com.bush.pharmacy_web_app.repository.dto.cart.CartItemReadDto;
import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineReadDto;
import com.bush.pharmacy_web_app.repository.entity.cart.CartItems;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.orders.MedicineReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemsReadMapper implements DtoMapper<CartItems, CartItemReadDto> {
    private final MedicineReadMapper mapper;
    @Override
    public CartItemReadDto map(CartItems obj) {
        MedicineReadDto medicineDto = Optional.ofNullable(obj.getMedicine())
                .map(mapper::map)
                .orElse(null);
        return new CartItemReadDto(medicineDto, obj.getAmount());
    }
}
