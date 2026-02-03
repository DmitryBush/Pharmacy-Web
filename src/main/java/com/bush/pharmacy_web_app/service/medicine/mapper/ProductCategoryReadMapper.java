package com.bush.pharmacy_web_app.service.medicine.mapper;

import com.bush.pharmacy_web_app.model.dto.medicine.ProductCategoryDto;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMapping;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductCategoryReadMapper implements DtoMapper<ProductTypeMapping, ProductCategoryDto> {
    private final MedicineTypeReadMapper medicineTypeReadMapper;
    @Override
    public ProductCategoryDto map(ProductTypeMapping obj) {
        var type = Optional.ofNullable(obj.getId().getType())
                .map(medicineTypeReadMapper::map)
                .orElseThrow();
        return new ProductCategoryDto(type, obj.getIsMain());
    }
}
