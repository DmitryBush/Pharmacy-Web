package com.bush.search.domain.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductFilter(String name,
                            String type,
                            List<String> manufacturers,
                            List<String> countries,
                            List<String> activeIngredients,
                            BigDecimal minPrice,
                            BigDecimal maxPrice,
                            Integer recipe) {
}
