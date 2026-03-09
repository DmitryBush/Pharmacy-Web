package com.bush.pharmacy_web_app.service.manufacturer.mapper;

import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerReadDto;
import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ManufacturerReadMapper {
    @Mapping(target = "country", source = "country.country")
    ManufacturerReadDto mapToManufacturer(Manufacturer manufacturer);
}
