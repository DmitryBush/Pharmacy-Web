package com.bush.search.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProductFilter(String name,
                            @NotNull String type,
                            List<String> manufacturers,
                            List<String> countries,
                            List<String> activeIngredients,
                            BigDecimal minPrice,
                            BigDecimal maxPrice,
                            @NotNull Integer recipe) {
}
