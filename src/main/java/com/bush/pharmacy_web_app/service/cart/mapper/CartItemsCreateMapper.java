package com.bush.pharmacy_web_app.service.cart.mapper;

import com.bush.pharmacy_web_app.repository.medicine.MedicineRepository;
import com.bush.pharmacy_web_app.model.dto.cart.CartItemCreateDto;
import com.bush.pharmacy_web_app.model.entity.cart.CartItems;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
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
        toObj.setProduct(medicine);
        return toObj;
    }
}
