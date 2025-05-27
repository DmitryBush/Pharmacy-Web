package com.bush.pharmacy_web_app.repository.dto.cart;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicinePreviewReadDto;

public record CartItemReadDto(MedicinePreviewReadDto medicine,
                              Integer amount) {
}
