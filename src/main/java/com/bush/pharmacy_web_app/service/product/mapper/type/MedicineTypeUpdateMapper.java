package com.bush.pharmacy_web_app.service.product.mapper.type;

import com.bush.pharmacy_web_app.model.dto.product.ProductTypeUpdateDto;
import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import com.bush.pharmacy_web_app.repository.product.ProductTypeRepository;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineTypeUpdateMapper implements DtoMapper<ProductTypeUpdateDto, ProductType> {
    private final ProductTypeRepository productTypeRepository;
    @Override
    public ProductType map(ProductTypeUpdateDto obj) {
        return copyObj(obj, new ProductType());
    }

    @Override
    public ProductType map(ProductTypeUpdateDto fromObj, ProductType toObj) {
        return copyObj(fromObj, toObj);
    }

    private ProductType copyObj(ProductTypeUpdateDto fromObj, ProductType toObj) {
        Optional.ofNullable(fromObj.type())
                .ifPresent(toObj::setName);
        Optional.ofNullable(fromObj.slug())
                .ifPresent(toObj::setSlug);
        Optional.ofNullable(fromObj.parent())
                .map(productTypeRepository::findByName)
                .ifPresentOrElse(parent ->
                        parent.ifPresentOrElse(toObj::setParent, parent::orElseThrow), () -> toObj.setParent(null));
        return toObj;
    }
}
