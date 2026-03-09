package com.bush.search.service.supplier.mapper;

import com.bush.search.domain.document.supplier.Supplier;
import com.bush.search.domain.index.supplier.SupplierPayload;
import com.bush.search.service.address.mapper.AddressCreateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = AddressCreateMapper.class)
public interface SupplierCreateMapper {
    Supplier mapToSupplier(SupplierPayload supplierPayload);
}
