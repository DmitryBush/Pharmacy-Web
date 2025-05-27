package com.bush.pharmacy_web_app.repository.dto.orders;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicinePreviewReadDto;

import java.math.BigDecimal;

public record OrderItemReadDto(Long id,
                               Integer amount,
                               BigDecimal price,
                               MedicinePreviewReadDto medicine) {
}
