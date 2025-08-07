package com.bush.pharmacy_web_app.model.dto.medicine;

import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerReadDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record MedicineReadDto(Long id,
                              String name,
                              ManufacturerReadDto manufacturer,
                              String type,
                              BigDecimal price,
                              List<MedicineImageReadDto> imagePaths,
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
