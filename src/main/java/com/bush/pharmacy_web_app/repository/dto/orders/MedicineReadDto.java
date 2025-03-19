package com.bush.pharmacy_web_app.repository.dto.orders;

import java.math.BigDecimal;

public record MedicineReadDto(String name,
                              String manufacturer,
                              BigDecimal price) {
}
