package com.bush.search.repository.filter;

import java.util.Map;

public record ProductAggregation(Map<String, Long> manufacturers,
                                 Map<String, Long> countries,
                                 Map<String, Long> activeIngredients) {
}
