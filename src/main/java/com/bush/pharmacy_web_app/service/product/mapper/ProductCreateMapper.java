package com.bush.pharmacy_web_app.service.product.mapper;

import com.bush.pharmacy_web_app.model.dto.product.ProductCreateDto;
import com.bush.pharmacy_web_app.model.entity.Supplier;
import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.model.entity.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "name", source = "productCreateDto.name")
    @Mapping(target = "manufacturer", source = "manufacturer")
    @Mapping(target = "supplier", source = "supplier")
    @Mapping(target = "dailyFeaturedProductReference", ignore = true)
    @Mapping(target = "image", ignore = true)
    Product mapToProduct(final ProductCreateDto productCreateDto, final Supplier supplier,
                         final Manufacturer manufacturer);
}
