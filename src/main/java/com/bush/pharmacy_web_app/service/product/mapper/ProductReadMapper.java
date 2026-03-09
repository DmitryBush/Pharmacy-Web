package com.bush.pharmacy_web_app.service.product.mapper;

import com.bush.pharmacy_web_app.model.dto.product.ProductAdminReadDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductImageReadDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductReadDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductTypeMappingDto;
import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import com.bush.pharmacy_web_app.model.entity.product.Product;
import com.bush.pharmacy_web_app.model.entity.product.ProductImage;
import com.bush.pharmacy_web_app.model.entity.product.ProductTypeMapping;
import com.bush.pharmacy_web_app.model.entity.product.ProductTypeMappingId;
import com.bush.pharmacy_web_app.service.manufacturer.mapper.ManufacturerReadMapper;
import com.bush.pharmacy_web_app.service.supplier.mapper.SupplierReadMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ManufacturerReadMapper.class, SupplierReadMapper.class, ProductImageReadMapper.class})
public interface ProductReadMapper {
    @Mapping(target = "type", source = "type", qualifiedByName = "mapType")
    @Mapping(target = "imagePaths", source = "image", qualifiedByName = "mapToMedicineImageReadDtoList")
    ProductPreviewReadDto mapToMedicinePreviewReadDto(Product product);

    @Mapping(target = "type", source = "type", qualifiedByName = "mapType")
    @Mapping(target = "imagePaths", source = "image", qualifiedByName = "mapToMedicineImageReadDtoList")
    ProductReadDto mapToMedicineReadDto(Product product);

    @Mapping(target = "types", source = "type", qualifiedByName = "mapTypeList")
    @Mapping(target = "imagePaths", source = "image", qualifiedByName = "mapToMedicineImageReadDtoList")
    ProductAdminReadDto mapToMedicineAdminReadDto(Product product);

    @Named("mapToMedicineImageReadDtoList")
    default List<ProductImageReadDto> mapToMedicineImageReadDtoList(List<ProductImage> imageList) {
        return imageList.stream()
                .map(this::mapToMedicineImageReadDto)
                .toList();
    }

    ProductImageReadDto mapToMedicineImageReadDto(ProductImage image);

    @Named("mapType")
    default String mapType(List<ProductTypeMapping> typeMapping) {
        return typeMapping.stream()
                .filter(ProductTypeMapping::getIsMain)
                .map(ProductTypeMapping::getId)
                .map(ProductTypeMappingId::getType)
                .map(ProductType::getName)
                .findFirst()
                .orElse(null);
    }

    @Named("mapTypeList")
    default List<ProductTypeMappingDto> mapTypeList(List<ProductTypeMapping> typeMappings) {
        return typeMappings.stream()
                .map(typeMapping -> {
                    String typeName = typeMapping.getId().getType().getName();
                    String parentTypeName = Optional.ofNullable(typeMapping.getId().getType().getParent())
                            .map(ProductType::getName)
                            .orElse(null);
                    return new ProductTypeMappingDto(typeName, parentTypeName, typeMapping.getIsMain());
                })
                .toList();
    }
}
