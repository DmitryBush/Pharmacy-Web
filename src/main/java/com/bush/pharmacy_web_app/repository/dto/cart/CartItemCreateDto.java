package com.bush.pharmacy_web_app.repository.dto.cart;

public record CartItemCreateDto(Integer amount,
                                MedicineCartReadDto medicine) {
}
