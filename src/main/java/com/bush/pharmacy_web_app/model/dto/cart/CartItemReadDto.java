package com.bush.pharmacy_web_app.model.dto.cart;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;

public record CartItemReadDto(MedicinePreviewReadDto medicine,
                              Integer amount) {
}
