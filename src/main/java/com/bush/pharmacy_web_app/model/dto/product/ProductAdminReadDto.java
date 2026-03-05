package com.bush.pharmacy_web_app.model.dto.product;

import com.bush.pharmacy_web_app.model.dto.supplier.SupplierReadDto;
import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerReadDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductAdminReadDto(Long id,
                                  String name,
                                  SupplierReadDto supplier,
                                  ManufacturerReadDto manufacturer,
                                  List<ProductTypeMappingDto> types,
                                  BigDecimal price,
                                  Boolean recipe,
                                  List<ProductImageReadDto> imagePaths,
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
