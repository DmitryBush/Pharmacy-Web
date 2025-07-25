package com.bush.pharmacy_web_app.repository.medicine.filter;

import java.math.BigDecimal;
import java.util.List;

public record MedicineFilter(String name,
                             List<String> type,
                             List<String> manufacturer,
                             BigDecimal minPrice,
                             BigDecimal maxPrice,
                             Integer recipe) {
}
