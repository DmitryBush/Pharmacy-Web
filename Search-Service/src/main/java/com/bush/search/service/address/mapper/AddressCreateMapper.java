package com.bush.search.service.address.mapper;

import com.bush.search.domain.document.address.Address;
import com.bush.search.domain.index.address.AddressPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressCreateMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(addressPayload.id()))")
    @Mapping(target = "addressId", source = "id")
    Address mapToAddress(AddressPayload addressPayload);
}
