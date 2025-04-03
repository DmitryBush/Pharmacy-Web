package com.bush.pharmacy_web_app.repository.mapper.cart;

import com.bush.pharmacy_web_app.repository.MedicineRepository;
import com.bush.pharmacy_web_app.repository.dto.cart.CartItemCreateDto;
import com.bush.pharmacy_web_app.repository.entity.CartItems;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemsCreateMapper implements DtoMapper<CartItemCreateDto, CartItems> {
    private final MedicineRepository medicineRepository;
    @Override
    public CartItems map(CartItemCreateDto obj) {
        return copyObj(obj, new CartItems());
    }

    @Override
    public CartItems map(CartItemCreateDto fromObj, CartItems toObj) {
        return copyObj(fromObj, toObj);
    }

    public CartItems copyObj(CartItemCreateDto fromObj, CartItems toObj) {
        var medicine = medicineRepository.findById(fromObj.medicine().id())
                        .orElseThrow();
        toObj.setAmount(fromObj.amount());
        toObj.setMedicine(medicine);
        return toObj;
    }
}
