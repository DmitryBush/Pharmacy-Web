package com.bush.pharmacy_web_app.model.dto.product;

import com.bush.pharmacy_web_app.model.dto.supplier.SupplierCreateDto;
import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductCreateDto(Long id,
                               @NotNull @NotBlank @Length(max = 128) String name,
                               @NotNull @Valid List<ProductTypeMappingDto> type,
                               @NotNull @Valid ManufacturerCreateDto manufacturer,
                               @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal price,
                               @NotNull Boolean recipe,
                               @NotNull @Valid SupplierCreateDto supplier,
                               @NotNull @NotBlank @Length(max = 25) String activeIngredient,
                               @NotNull @NotBlank @Length(max = 25) String expirationDate,
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
