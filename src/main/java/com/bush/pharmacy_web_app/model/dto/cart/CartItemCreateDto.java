package com.bush.pharmacy_web_app.model.dto.cart;

public record CartItemCreateDto(Integer amount,
                                MedicineCartReadDto medicine) {
}
