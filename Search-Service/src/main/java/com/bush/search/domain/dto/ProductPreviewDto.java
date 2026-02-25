package com.bush.search.domain.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductPreviewDto(Long id,
                                String name,
                                String type,
                                BigDecimal price,
                                List<Long> imagePaths,
                                ManufacturerReadDto manufacturer) {
}
