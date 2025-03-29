package com.bush.pharmacy_web_app.repository.dto.orders;

public record CartItemsReadDto(MedicineReadDto medicine,
                               Integer amount) {
}
