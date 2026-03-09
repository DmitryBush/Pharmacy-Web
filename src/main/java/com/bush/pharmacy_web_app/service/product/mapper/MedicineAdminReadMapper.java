package com.bush.pharmacy_web_app.service.product.mapper;

import com.bush.pharmacy_web_app.model.dto.product.ProductAdminReadDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductImageReadDto;
import com.bush.pharmacy_web_app.model.entity.product.Product;
import com.bush.pharmacy_web_app.service.product.mapper.list.ListProductCategoryReadMapper;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.supplier.mapper.LegacySupplierReadMapper;
import com.bush.pharmacy_web_app.service.manufacturer.mapper.LegacyManufacturerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineAdminReadMapper implements DtoMapper<Product, ProductAdminReadDto> {
    private final LegacyManufacturerReadMapper manufacturerReadMapper;
    private final LegacySupplierReadMapper legacySupplierReadMapper;
    private final ListProductCategoryReadMapper listProductCategoryReadMapper;
    @Override
    public ProductAdminReadDto map(Product obj) {
        var supplier = Optional.ofNullable(obj.getSupplier())
                .map(legacySupplierReadMapper::map)
                .orElseThrow();
        var manufacturer = Optional.ofNullable(obj.getManufacturer())
                .map(manufacturerReadMapper::map)
                .orElseThrow();
        var type = Optional.ofNullable(obj.getType())
                .map(listProductCategoryReadMapper::map)
                .orElseThrow();
        var imagePaths = Optional.ofNullable(obj.getImage())
                .map(list -> list
                        .stream()
                        .map(image -> new ProductImageReadDto(image.getId()))
                        .toList())
                .orElse(Collections.emptyList());
        return ProductAdminReadDto.builder()
                .id(obj.getId())
                .name(obj.getName())
                .supplier(supplier)
                .manufacturer(manufacturer)
                .types(type)
                .price(obj.getPrice())
                .recipe(obj.getRecipe())
                .imagePaths(imagePaths)
                .activeIngredient(obj.getActiveIngredient())
                .expirationDate(obj.getExpirationDate())
                .composition(obj.getComposition())
                .indication(obj.getIndication())
                .contraindication(obj.getContraindication())
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
