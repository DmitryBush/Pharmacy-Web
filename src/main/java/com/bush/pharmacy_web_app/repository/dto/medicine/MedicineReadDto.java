package com.bush.pharmacy_web_app.repository.dto.medicine;

import com.bush.pharmacy_web_app.repository.dto.manufacturer.ManufacturerReadDto;

import java.math.BigDecimal;
import java.util.List;

public record MedicineReadDto(Long id,
                              String name,
                              ManufacturerReadDto manufacturer,
                              String type,
                              BigDecimal price,
                              List<MedicineImageReadDto> imagePaths) {
}
