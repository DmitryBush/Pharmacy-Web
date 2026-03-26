package com.bush.pharmacy_web_app.service.address.mapper;

import com.bush.pharmacy_web_app.model.dto.address.AddressCreateDto;
import com.bush.pharmacy_web_app.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressCreateMapper {
    Address mapToAddress(AddressCreateDto createDto);
    @Named("updateAddress")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "district", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "settlement", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "street", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "house", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "apartment", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "postalCode", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address updateAddress(@MappingTarget Address address, AddressCreateDto createDto);
}
