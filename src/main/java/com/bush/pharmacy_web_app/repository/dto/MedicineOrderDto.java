package com.bush.pharmacy_web_app.repository.dto;

import java.math.BigDecimal;

public record MedicineOrderDto(String name,
                               String manufacturer,
                               BigDecimal price) {
}
