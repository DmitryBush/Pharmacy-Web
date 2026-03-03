package com.bush.pharmacy_web_app.service.medicine.mapper.list;

import com.bush.pharmacy_web_app.model.dto.medicine.ProductTypeMappingDto;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMapping;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMappingId;
import com.bush.pharmacy_web_app.service.medicine.mapper.type.MedicineTypeMapper;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListProductCategoryCreateMapper implements DtoMapper<List<ProductTypeMappingDto>, List<ProductTypeMapping>> {
    private final MedicineTypeMapper typeCreateMapper;
    @Override
    public List<ProductTypeMapping> map(List<ProductTypeMappingDto> obj) {
        return obj.stream()
                .map(productCategoryDto -> copyObj(productCategoryDto, new ProductTypeMapping()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ProductTypeMapping copyObj(ProductTypeMappingDto fromObj, ProductTypeMapping toObj) {
//        return Optional.ofNullable(fromObj.type())
//                .map(typeCreateMapper::map)
//                .map(type -> {
//                    toObj.setId(ProductTypeMappingId.builder()
//                            .type(type)
//                            .build());
//                    toObj.setIsMain(fromObj.isMain());
//                    return toObj;
//                })
//                .orElseThrow();
        return null;
    }
}
