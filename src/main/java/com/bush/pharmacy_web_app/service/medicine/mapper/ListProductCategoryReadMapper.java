package com.bush.pharmacy_web_app.service.medicine.mapper;

import com.bush.pharmacy_web_app.model.dto.medicine.ProductCategoryDto;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMapping;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListProductCategoryReadMapper implements DtoMapper<List<ProductTypeMapping>, List<ProductCategoryDto>> {
    private final ProductCategoryReadMapper productCategoryReadMapper;
    @Override
    public List<ProductCategoryDto> map(List<ProductTypeMapping> obj) {
        return obj.stream()
                .map(productCategoryReadMapper::map)
                .toList();
    }
}
