package com.bush.pharmacy_web_app.repository.dto.catalog;

import java.math.BigDecimal;

public record MedicineCreateDto(String name,
                                String type,
                                String manufacturer,
                                BigDecimal price,
                                Boolean recipe,
                                SupplierCreateDto supplier) {
}
