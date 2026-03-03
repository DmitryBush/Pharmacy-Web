package com.bush.pharmacy_web_app.service.product.mapper.list;

import com.bush.pharmacy_web_app.repository.product.ProductTypeRepository;
import com.bush.pharmacy_web_app.model.dto.product.ProductTypeDto;
import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListMedicineTypeCreateMapper implements DtoMapper<List<ProductTypeDto>, List<ProductType>> {
    private final ProductTypeRepository productTypeRepository;
    @Override
    public List<ProductType> map(List<ProductTypeDto> obj) {
        return obj.stream()
                .map(medicineTypeDto -> copyObj(medicineTypeDto, new ProductType()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ProductType copyObj(ProductTypeDto fromObj, ProductType toObj) {
        return productTypeRepository.findByName(fromObj.name())
                .orElseGet(() -> {
                    var parent = productTypeRepository.findByName(fromObj.parent())
                                    .orElseThrow();

                    toObj.setName(fromObj.name());
                    toObj.setParent(parent);
                    toObj.setChildTypes(Collections.emptyList());
                    return toObj;
                });
    }
}
