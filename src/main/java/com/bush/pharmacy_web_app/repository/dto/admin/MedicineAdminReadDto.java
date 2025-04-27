package com.bush.pharmacy_web_app.repository.dto.admin;

import com.bush.pharmacy_web_app.repository.dto.catalog.SupplierReadDto;
import com.bush.pharmacy_web_app.repository.dto.manufacturer.ManufacturerReadDto;
import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineImageReadDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record MedicineAdminReadDto(Long id,
                                   String name,
                                   SupplierReadDto supplier,
                                   ManufacturerReadDto manufacturer,
                                   String type,
                                   BigDecimal price,
                                   Boolean recipe,
                                   List<MedicineImageReadDto> imagePaths,
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
