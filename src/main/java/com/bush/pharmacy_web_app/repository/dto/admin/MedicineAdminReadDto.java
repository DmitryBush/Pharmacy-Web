package com.bush.pharmacy_web_app.repository.dto.admin;

import com.bush.pharmacy_web_app.repository.dto.catalog.SupplierReadDto;
import com.bush.pharmacy_web_app.repository.dto.manufacturer.ManufacturerReadDto;

import java.math.BigDecimal;

public record MedicineAdminReadDto(Long id,
                                   String name,
                                   SupplierReadDto supplier,
                                   ManufacturerReadDto manufacturer,
                                   String type,
                                   BigDecimal price) {
}
