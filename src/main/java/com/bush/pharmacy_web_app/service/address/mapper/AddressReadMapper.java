package com.bush.pharmacy_web_app.service.address.mapper;

import com.bush.pharmacy_web_app.model.dto.address.AddressReadDto;
import com.bush.pharmacy_web_app.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressReadMapper {
    AddressReadDto mapToAddressReadDto(Address address);
}
