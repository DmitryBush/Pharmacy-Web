package com.bush.search.service.manufacturer.mapper;

import com.bush.search.domain.document.manufacturer.Manufacturer;
import com.bush.search.domain.dto.ManufacturerReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ManufacturerReadMapper {
    @Mapping(target = "id", source = "manufacturerId")
    @Mapping(target = "countryName", source = "country.countryName")
    ManufacturerReadDto mapToManufacturerReadDto(Manufacturer manufacturer);
}
