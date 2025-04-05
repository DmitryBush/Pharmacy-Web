package com.bush.pharmacy_web_app.repository.dto.cart;

import com.bush.pharmacy_web_app.repository.dto.orders.MedicineReadDto;

public record CartItemReadDto(MedicineReadDto medicine,
                              Integer amount) {
}
