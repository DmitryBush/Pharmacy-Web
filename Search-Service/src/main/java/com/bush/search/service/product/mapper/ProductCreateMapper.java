package com.bush.search.service.product.mapper;

import com.bush.search.domain.document.product.Product;
import com.bush.search.domain.index.product.ProductPayload;
import com.bush.search.service.manufacturer.mapper.ManufacturerCreateMapper;
import com.bush.search.service.supplier.mapper.SupplierCreateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = String.class,
        uses = {ManufacturerCreateMapper.class, SupplierCreateMapper.class, ProductTypeCreateMapper.class})
public interface ProductCreateMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(productPayload.id()))")
    @Mapping(target = "productId", source = "id")
    Product mapToProduct(ProductPayload productPayload);
}
