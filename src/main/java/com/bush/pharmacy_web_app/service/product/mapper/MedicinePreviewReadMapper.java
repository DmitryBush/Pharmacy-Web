package com.bush.pharmacy_web_app.service.product.mapper;

import com.bush.pharmacy_web_app.model.dto.product.ProductImageReadDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;
import com.bush.pharmacy_web_app.model.entity.product.Product;
import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import com.bush.pharmacy_web_app.model.entity.product.ProductTypeMapping;
import com.bush.pharmacy_web_app.model.entity.product.ProductTypeMappingId;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.manufacturer.mapper.LegacyManufacturerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicinePreviewReadMapper implements DtoMapper<Product, ProductPreviewReadDto> {
    private final LegacyManufacturerReadMapper manufacturerReadMapper;
    @Override
    public ProductPreviewReadDto map(Product obj) {
        var type = Optional.ofNullable(obj.getType())
                .map(productCategories -> productCategories.stream()
                        .filter(ProductTypeMapping::getIsMain)
                        .map(ProductTypeMapping::getId)
                        .map(ProductTypeMappingId::getType)
                        .map(ProductType::getName)
                        .findFirst()
                        .orElseThrow())
                .orElseThrow();
        var manufacturer = Optional.ofNullable(obj.getManufacturer())
                .map(manufacturerReadMapper::map)
                .orElse(null);
        var imagePaths = Optional.ofNullable(obj.getImage())
                .map(list -> list
                        .stream()
                        .map(image -> new ProductImageReadDto(image.getId()))
                        .toList())
                .orElse(Collections.emptyList());
        return new ProductPreviewReadDto(obj.getId(), obj.getName(), manufacturer, type, obj.getPrice(), imagePaths);
    }
}
