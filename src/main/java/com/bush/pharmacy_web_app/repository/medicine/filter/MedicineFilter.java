package com.bush.pharmacy_web_app.repository.medicine.filter;

import java.math.BigDecimal;
import java.util.List;

public record MedicineFilter(String name,
                             String type,
                             List<String> manufacturer,
                             List<String> countries,
                             List<String> activeIngredients,
                             BigDecimal minPrice,
                             BigDecimal maxPrice,
                             Integer recipe) {
}
