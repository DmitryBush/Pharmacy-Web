package com.bush.pharmacy_web_app.model.dto.product;

import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerReadDto;

import java.math.BigDecimal;
import java.util.List;

public record ProductPreviewReadDto(Long id,
                                    String name,
                                    ManufacturerReadDto manufacturer,
                                    String type,
                                    BigDecimal price,
                                    List<ProductImageReadDto> imagePaths) {
}
