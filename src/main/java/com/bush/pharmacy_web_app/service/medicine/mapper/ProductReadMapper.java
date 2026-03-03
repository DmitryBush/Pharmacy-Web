package com.bush.pharmacy_web_app.service.medicine.mapper;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicineAdminReadDto;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicineImageReadDto;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicineReadDto;
import com.bush.pharmacy_web_app.model.dto.medicine.ProductTypeMappingDto;
import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.model.entity.medicine.Product;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductImage;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMapping;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductTypeMappingId;
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
    MedicinePreviewReadDto mapToMedicinePreviewReadDto(Product product);

    @Mapping(target = "type", source = "type", qualifiedByName = "mapType")
    @Mapping(target = "imagePaths", source = "image", qualifiedByName = "mapToMedicineImageReadDtoList")
    MedicineReadDto mapToMedicineReadDto(Product product);

    @Mapping(target = "types", source = "type", qualifiedByName = "mapTypeList")
    @Mapping(target = "imagePaths", source = "image", qualifiedByName = "mapToMedicineImageReadDtoList")
    MedicineAdminReadDto mapToMedicineAdminReadDto(Product product);

    @Named("mapToMedicineImageReadDtoList")
    default List<MedicineImageReadDto> mapToMedicineImageReadDtoList(List<ProductImage> imageList) {
        return imageList.stream()
                .map(this::mapToMedicineImageReadDto)
                .toList();
    }

    MedicineImageReadDto mapToMedicineImageReadDto(ProductImage image);

    @Named("mapType")
    default String mapType(List<ProductTypeMapping> typeMapping) {
        return typeMapping.stream()
                .filter(ProductTypeMapping::getIsMain)
                .map(ProductTypeMapping::getId)
                .map(ProductTypeMappingId::getType)
                .map(MedicineType::getName)
                .findFirst()
                .orElse(null);
    }

    @Named("mapTypeList")
    default List<ProductTypeMappingDto> mapTypeList(List<ProductTypeMapping> typeMappings) {
        return typeMappings.stream()
                .map(typeMapping -> {
                    String typeName = typeMapping.getId().getType().getName();
                    String parentTypeName = Optional.ofNullable(typeMapping.getId().getType().getParent())
                            .map(MedicineType::getName)
                            .orElse(null);
                    return new ProductTypeMappingDto(typeName, parentTypeName, typeMapping.getIsMain());
                })
                .toList();
    }
}
