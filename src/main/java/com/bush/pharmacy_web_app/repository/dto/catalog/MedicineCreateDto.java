package com.bush.pharmacy_web_app.repository.dto.catalog;

import com.bush.pharmacy_web_app.repository.dto.manufacturer.ManufacturerCreateDto;
import com.bush.pharmacy_web_app.repository.dto.orders.MedicineTypeCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record MedicineCreateDto(Long id,
                                @NotNull @NotBlank @Length(max = 128) String name,
                                @NotNull String type,
                                @NotNull @Valid ManufacturerCreateDto manufacturer,
                                @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal price,
                                @NotNull Boolean recipe,
                                @NotNull @Valid SupplierCreateDto supplier) {
}
