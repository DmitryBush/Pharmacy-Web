package com.bush.search.service.country.mapper;

import com.bush.search.domain.document.manufacturer.Country;
import com.bush.search.domain.index.manufacturer.CountryPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryCreateMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(countryPayload.id()))")
    @Mapping(target = "countryName", source = "country")
    Country mapToCountry(CountryPayload countryPayload);
}
