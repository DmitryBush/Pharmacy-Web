package com.bush.pharmacy_web_app.repository.dto.orders;

import com.bush.pharmacy_web_app.repository.dto.ManufacturerReadDto;

import java.math.BigDecimal;

public record MedicineReadDto(Long id,
                              String name,
                              ManufacturerReadDto manufacturer,
                              String type,
                              BigDecimal price) {
}
