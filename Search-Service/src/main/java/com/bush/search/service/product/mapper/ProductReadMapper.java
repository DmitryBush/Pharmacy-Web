package com.bush.search.service.product.mapper;

import com.bush.search.domain.document.product.Product;
import com.bush.search.domain.document.product.ProductType;
import com.bush.search.domain.dto.ProductPreviewDto;
import com.bush.search.service.manufacturer.mapper.ManufacturerReadMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ManufacturerReadMapper.class)
public interface ProductReadMapper {
    @Mapping(target = "id", source = "productId")
    @Mapping(target = "type", qualifiedByName = "mapMainType")
    @Mapping(target = "imagePaths", source = "image")
    ProductPreviewDto mapToProductPreviewDto(Product product);

    @Named("mapMainType")
    default String mapMainType(List<ProductType> types) {
        return types.stream()
                .filter(ProductType::getIsMain)
                .map(ProductType::getTypeName)
                .findFirst()
                .orElse(null);
    }
}
