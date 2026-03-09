package com.bush.pharmacy_web_app.model.dto.product;

import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerReadDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductReadDto(Long id,
                             String name,
                             ManufacturerReadDto manufacturer,
                             String type,
                             BigDecimal price,
                             List<ProductImageReadDto> imagePaths,
                             Boolean recipe,
                             String activeIngredient,
                             String expirationDate,
                             String composition,
                             String indication,
                             String contraindication,
                             String sideEffect,
                             String interaction,
                             String admissionCourse,
                             String overdose,
                             String specialInstruction,
                             String storageCondition,
                             String releaseForm) {
}
