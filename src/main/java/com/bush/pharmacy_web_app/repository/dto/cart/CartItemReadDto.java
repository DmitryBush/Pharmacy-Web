package com.bush.pharmacy_web_app.repository.dto.cart;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineReadDto;

public record CartItemReadDto(MedicineReadDto medicine,
                              Integer amount) {
}
