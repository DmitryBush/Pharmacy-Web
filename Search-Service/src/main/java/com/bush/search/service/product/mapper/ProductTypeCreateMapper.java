package com.bush.search.service.product.mapper;

import com.bush.search.domain.document.product.ProductType;
import com.bush.search.domain.index.product.ProductTypeMappingPayload;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = String.class)
public interface ProductTypeCreateMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(productTypeMappingPayload.id()))")
    @Mapping(target = "typeId", source = "id")
    @Mapping(target = "typeName", source = "name")
    @Mapping(target = "typeSlug", source = "slug")
    @Mapping(target = "slugInheritanceChain", source = "productTypeMappingPayload",
            qualifiedByName = "mapSlugInheritanceChain")
    ProductType mapToProductType(ProductTypeMappingPayload productTypeMappingPayload);

    @Named("mapSlugInheritanceChain")
    default List<String> mapSlugInheritanceChain(ProductTypeMappingPayload productTypeMappingPayload) {
        return Stream.iterate(productTypeMappingPayload, Objects::nonNull, ProductTypeMappingPayload::parent)
                .map(ProductTypeMappingPayload::slug)
                .toList();
    }
}
