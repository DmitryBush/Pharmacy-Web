package com.bush.pharmacy_web_app.service.medicine.mapper.list;

import com.bush.pharmacy_web_app.model.dto.medicine.ProductTypeMappingDto;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMapping;
import com.bush.pharmacy_web_app.service.medicine.mapper.type.ProductCategoryReadMapper;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListProductCategoryReadMapper implements DtoMapper<List<ProductTypeMapping>, List<ProductTypeMappingDto>> {
    private final ProductCategoryReadMapper productCategoryReadMapper;
    @Override
    public List<ProductTypeMappingDto> map(List<ProductTypeMapping> obj) {
        return obj.stream()
                .map(productCategoryReadMapper::map)
                .toList();
    }
}
