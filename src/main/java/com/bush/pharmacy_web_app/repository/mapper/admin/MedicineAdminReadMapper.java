package com.bush.pharmacy_web_app.repository.mapper.admin;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineAdminReadDto;
import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineImageReadDto;
import com.bush.pharmacy_web_app.repository.entity.medicine.Medicine;
import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.repository.entity.medicine.ProductCategories;
import com.bush.pharmacy_web_app.repository.entity.medicine.ProductCategoriesId;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.manufacturer.ManufacturerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineAdminReadMapper implements DtoMapper<Medicine, MedicineAdminReadDto> {
    private final ManufacturerReadMapper manufacturerReadMapper;
    private final SupplierReadMapper supplierReadMapper;
    @Override
    public MedicineAdminReadDto map(Medicine obj) {
        var supplier = Optional.ofNullable(obj.getSupplier())
                .map(supplierReadMapper::map)
                .orElseThrow();
        var manufacturer = Optional.ofNullable(obj.getManufacturer())
                .map(manufacturerReadMapper::map)
                .orElseThrow();
        var type = Optional.ofNullable(obj.getType())
                .map(productCategories -> productCategories.stream()
                        .filter(ProductCategories::getIsMain)
                        .map(ProductCategories::getId)
                        .map(ProductCategoriesId::getType)
                        .map(MedicineType::getType)
                        .findFirst()
                        .orElseThrow())
                .orElseThrow();
        var imagePaths = Optional.ofNullable(obj.getImage())
                .map(list -> list
                        .stream()
                        .map(image -> new MedicineImageReadDto(image.getId(), image.getPath()))
                        .toList())
                .orElse(Collections.emptyList());
        return MedicineAdminReadDto.builder()
                .id(obj.getId())
                .name(obj.getName())
                .supplier(supplier)
                .manufacturer(manufacturer)
                .type(type)
                .price(obj.getPrice())
                .recipe(obj.getRecipe())
                .imagePaths(imagePaths)
                .activeIngredient(obj.getActiveIngredient())
                .expirationDate(obj.getExpirationDate())
                .composition(obj.getComposition())
                .indication(obj.getIndication())
                .contraindication(obj.getContraindications())
                .sideEffect(obj.getSideEffect())
                .interaction(obj.getInteraction())
                .admissionCourse(obj.getAdmissionCourse())
                .overdose(obj.getOverdose())
                .specialInstruction(obj.getSpecialInstruction())
                .storageCondition(obj.getStorageCondition())
                .releaseForm(obj.getReleaseForm())
                .build();
    }
}
