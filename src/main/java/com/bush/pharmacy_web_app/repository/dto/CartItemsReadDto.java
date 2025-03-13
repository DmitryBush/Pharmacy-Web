package com.bush.pharmacy_web_app.repository.dto;

public record CartItemsReadDto(MedicineOrderDto medicine,
                               Integer amount) {
}
