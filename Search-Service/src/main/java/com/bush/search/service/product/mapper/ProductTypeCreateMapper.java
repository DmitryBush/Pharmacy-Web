package com.bush.search.service.product.mapper;

import com.bush.search.domain.document.product.ProductType;
import com.bush.search.domain.index.product.ProductTypeMappingPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = String.class)
public interface ProductTypeCreateMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(productTypeMappingPayload.id()))")
    @Mapping(target = "typeId", source = "id")
    @Mapping(target = "typeName", source = "type")
    @Mapping(target = "parent", source = "parent")
    ProductType mapToProductType(ProductTypeMappingPayload productTypeMappingPayload);
}
