package com.bush.pharmacy_web_app.service.supplier.mapper;

import com.bush.pharmacy_web_app.model.dto.supplier.SupplierReadDto;
import com.bush.pharmacy_web_app.model.entity.Supplier;
import com.bush.pharmacy_web_app.service.address.mapper.AddressReadMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = AddressReadMapper.class)
public interface SupplierReadMapper {
    SupplierReadDto mapToSupplier(Supplier supplier);
}
