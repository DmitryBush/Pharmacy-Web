package com.bush.pharmacy_web_app.repository.dto.catalog;

import com.bush.pharmacy_web_app.repository.dto.manufacturer.ManufacturerCreateDto;
import com.bush.pharmacy_web_app.repository.mapper.strategy.MergeStrategy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record MedicineCreateDto(Long id,
                                @NotNull @NotBlank @Length(max = 128) String name,
                                @NotNull String type,
                                @NotNull @Valid ManufacturerCreateDto manufacturer,
                                @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal price,
                                @NotNull Boolean recipe,
                                @NotNull @Valid SupplierCreateDto supplier,
                                List<MultipartFile> images,
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
    public static final MergeStrategy<MedicineCreateDto> IMAGES_STRATEGY_MERGE =
            ((from, to) -> new MedicineCreateDto(
            to.id,
            to.name,
            to.type,
            to.manufacturer,
            to.price,
            to.recipe,
            to.supplier,
            to.images != null ? to.images : from.images,
            to.activeIngredient,
            to.expirationDate,
            to.composition,
            to.indication,
            to.contraindication,
            to.sideEffect,
            to.interaction,
            to.admissionCourse,
            to.overdose,
            to.specialInstruction,
            to.storageCondition,
            to.releaseForm
            ));

}
