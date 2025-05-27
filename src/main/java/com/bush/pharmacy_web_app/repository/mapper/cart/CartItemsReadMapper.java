package com.bush.pharmacy_web_app.repository.mapper.cart;

import com.bush.pharmacy_web_app.repository.dto.cart.CartItemReadDto;
import com.bush.pharmacy_web_app.repository.dto.medicine.MedicinePreviewReadDto;
import com.bush.pharmacy_web_app.repository.entity.cart.CartItems;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.medicine.MedicinePreviewReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartItemsReadMapper implements DtoMapper<CartItems, CartItemReadDto> {
    private final MedicinePreviewReadMapper mapper;
    @Override
    public CartItemReadDto map(CartItems obj) {
        MedicinePreviewReadDto medicineDto = Optional.ofNullable(obj.getMedicine())
                .map(mapper::map)
                .orElse(null);
        return new CartItemReadDto(medicineDto, obj.getAmount());
    }
}
