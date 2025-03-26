package com.bush.pharmacy_web_app.repository.dto;

import java.math.BigDecimal;

public record MedicineOrderDto(String name,
                               ManufacturerReadDto manufacturer,
                               BigDecimal price) {
}
