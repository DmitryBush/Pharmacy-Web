package com.bush.pharmacy_web_app.service.product.mapper.list;

import com.bush.pharmacy_web_app.model.dto.product.ProductTypeMappingDto;
import com.bush.pharmacy_web_app.model.entity.product.ProductTypeMapping;
import com.bush.pharmacy_web_app.service.product.mapper.type.ProductCategoryReadMapper;
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
