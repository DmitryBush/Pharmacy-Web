package com.bush.pharmacy_web_app.service.medicine.mapper;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicineImageReadDto;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;
import com.bush.pharmacy_web_app.model.entity.medicine.Medicine;
import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductCategories;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductCategoriesId;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.manufacturer.mapper.ManufacturerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicinePreviewReadMapper implements DtoMapper<Medicine, MedicinePreviewReadDto> {
    private final ManufacturerReadMapper manufacturerReadMapper;
    @Override
    public MedicinePreviewReadDto map(Medicine obj) {
        var type = Optional.ofNullable(obj.getType())
                .map(productCategories -> productCategories.stream()
                        .filter(ProductCategories::getIsMain)
                        .map(ProductCategories::getId)
                        .map(ProductCategoriesId::getType)
                        .map(MedicineType::getType)
                        .findFirst()
                        .orElseThrow())
                .orElseThrow();
        var manufacturer = Optional.ofNullable(obj.getManufacturer())
                .map(manufacturerReadMapper::map)
                .orElse(null);
        var imagePaths = Optional.ofNullable(obj.getImage())
                .map(list -> list
                        .stream()
                        .map(image -> new MedicineImageReadDto(image.getId(), image.getPath()))
                        .toList())
                .orElse(Collections.emptyList());
        return new MedicinePreviewReadDto(obj.getId(), obj.getName(), manufacturer, type, obj.getPrice(), imagePaths);
    }
}
