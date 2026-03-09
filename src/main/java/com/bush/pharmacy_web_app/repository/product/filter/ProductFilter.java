package com.bush.pharmacy_web_app.repository.product.filter;

import java.math.BigDecimal;
import java.util.List;

public record ProductFilter(String name,
                            String type,
                            List<String> manufacturer,
                            List<String> countries,
                            List<String> activeIngredients,
                            BigDecimal minPrice,
                            BigDecimal maxPrice,
                            Integer recipe) {
}
