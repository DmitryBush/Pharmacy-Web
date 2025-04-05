package com.bush.pharmacy_web_app.repository.dto.orders;

import java.math.BigDecimal;

public record OrderItemReadDto(Long id,
                               Integer amount,
                               BigDecimal price,
                               MedicineReadDto medicine) {
}
