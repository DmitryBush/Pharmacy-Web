package com.bush.pharmacy_web_app.service.product.mapper.type;

import com.bush.pharmacy_web_app.model.dto.product.ProductTypeMappingDto;
import com.bush.pharmacy_web_app.model.entity.product.ProductTypeMapping;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductCategoryReadMapper implements DtoMapper<ProductTypeMapping, ProductTypeMappingDto> {
    private final MedicineTypeReadMapper medicineTypeReadMapper;
    @Override
    public ProductTypeMappingDto map(ProductTypeMapping obj) {
        var type = Optional.ofNullable(obj.getId().getType())
                .map(medicineTypeReadMapper::map)
                .orElseThrow();
        return new ProductTypeMappingDto(type.name(), type.parent(), obj.getIsMain());
    }
}
