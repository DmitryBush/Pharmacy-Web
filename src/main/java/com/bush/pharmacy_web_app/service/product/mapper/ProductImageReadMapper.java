package com.bush.pharmacy_web_app.service.product.mapper;

import com.bush.pharmacy_web_app.model.dto.product.ProductImageReadDto;
import com.bush.pharmacy_web_app.model.entity.product.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductImageReadMapper {
    ProductImageReadDto mapToMedicineImageReadDto(ProductImage productImage);
}
