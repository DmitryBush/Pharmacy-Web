package com.bush.pharmacy_web_app.repository.dto.orders;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineReadDto;

import java.math.BigDecimal;

public record OrderItemReadDto(Long id,
                               Integer amount,
                               BigDecimal price,
                               MedicineReadDto medicine) {
}
