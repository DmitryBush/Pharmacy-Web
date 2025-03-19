package com.bush.pharmacy_web_app.repository.dto.filters;

import java.math.BigDecimal;

public record MedicineFilter(String name,
                             String type,
                             String manufacturer,
                             BigDecimal price,
                             Boolean recipe) {
}
