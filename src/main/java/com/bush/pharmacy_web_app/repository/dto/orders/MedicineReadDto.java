package com.bush.pharmacy_web_app.repository.dto.orders;

import java.math.BigDecimal;

public record MedicineReadDto(Long id,
                              String name,
                              String manufacturer,
                              String type,
                              BigDecimal price) {
}
