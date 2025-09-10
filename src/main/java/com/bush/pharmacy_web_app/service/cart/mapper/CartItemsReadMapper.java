package com.bush.pharmacy_web_app.service.cart.mapper;

import com.bush.pharmacy_web_app.model.dto.cart.CartItemReadDto;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;
import com.bush.pharmacy_web_app.model.entity.cart.CartItems;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicinePreviewReadMapper;
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
