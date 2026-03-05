package com.bush.pharmacy_web_app.service.product.mapper.type;

import com.bush.pharmacy_web_app.model.dto.product.ProductTypeDto;
import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MedicineTypeReadMapper implements DtoMapper<ProductType, ProductTypeDto> {
    @Override
    public ProductTypeDto map(ProductType obj) {
        return Optional.ofNullable(obj.getParent())
                .map(parent -> new ProductTypeDto(obj.getId(), obj.getName(), obj.getSlug(), parent.getName()))
                .orElseGet(() -> new ProductTypeDto(obj.getId(), obj.getName(), obj.getSlug(), null));
    }
}
