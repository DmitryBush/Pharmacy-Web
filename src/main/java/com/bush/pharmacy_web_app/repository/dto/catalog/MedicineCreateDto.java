package com.bush.pharmacy_web_app.repository.dto.catalog;

import com.bush.pharmacy_web_app.repository.dto.manufacturer.ManufacturerCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public record MedicineCreateDto(Long id,
                                @NotNull @NotBlank @Length(max = 128) String name,
                                @NotNull String type,
                                @NotNull @Valid ManufacturerCreateDto manufacturer,
                                @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal price,
                                @NotNull Boolean recipe,
                                @NotNull @Valid SupplierCreateDto supplier,
                                @NotNull List<MultipartFile> image,
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
