package com.bush.pharmacy_web_app.repository.dto.catalog;

import com.bush.pharmacy_web_app.repository.dto.ManufacturerCreateDto;

import java.math.BigDecimal;

public record MedicineCreateDto(String name,
                                String type,
                                ManufacturerCreateDto manufacturer,
                                BigDecimal price,
                                Boolean recipe,
                                SupplierCreateDto supplier) {
}
