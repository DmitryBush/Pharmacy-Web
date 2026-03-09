package com.bush.search.service.manufacturer.mapper;

import com.bush.search.domain.document.manufacturer.Manufacturer;
import com.bush.search.domain.index.manufacturer.ManufacturerPayload;
import com.bush.search.service.country.mapper.CountryCreateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CountryCreateMapper.class)
public interface ManufacturerCreateMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(manufacturerPayload.id()))")
    @Mapping(target = "manufacturerId", source = "id")
    Manufacturer mapToManufacturer(ManufacturerPayload manufacturerPayload);
}
