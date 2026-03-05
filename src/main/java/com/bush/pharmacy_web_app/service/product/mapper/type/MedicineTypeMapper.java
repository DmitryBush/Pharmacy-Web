package com.bush.pharmacy_web_app.service.product.mapper.type;

import com.bush.pharmacy_web_app.repository.product.ProductTypeRepository;
import com.bush.pharmacy_web_app.model.dto.product.ProductTypeDto;
import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class MedicineTypeMapper implements DtoMapper<ProductTypeDto, ProductType> {
    private final ProductTypeRepository productTypeRepository;
    @Override
    public ProductType map(ProductTypeDto obj) {
        return copyObj(obj, new ProductType());
    }

    @Override
    public ProductType map(ProductTypeDto fromObj, ProductType toObj) {
        return copyObj(fromObj, toObj);
    }

    private ProductType copyObj(ProductTypeDto fromObj, ProductType toObj) {
        return productTypeRepository.findByName(fromObj.name())
                .orElseGet(() -> {
                    var parent = productTypeRepository.findByName(fromObj.parent()).orElse(null);

                    toObj.setName(fromObj.name());
                    toObj.setParent(parent);
                    toObj.setChildTypes(Collections.emptyList());
                    return toObj;
                });
    }
}
