package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.catalog.SupplierCreateDto;
import com.bush.pharmacy_web_app.repository.entity.Supplier;
import com.bush.pharmacy_web_app.repository.mapper.orders.AddressReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SupplierCreateMapper implements DtoMapper<SupplierCreateDto, Supplier>{
    private final AddressCreateMapper createMapper;
    @Override
    public Supplier map(SupplierCreateDto obj) {
        return copyObj(obj, new Supplier());
    }

    @Override
    public Supplier map(SupplierCreateDto fromObj, Supplier toObj) {
        return copyObj(fromObj, toObj);
    }

    private Supplier copyObj(SupplierCreateDto fromObj, Supplier toObj) {
        var address = Optional.ofNullable(fromObj.address())
                        .map(createMapper::map)
                                .orElseThrow();
        toObj.setName(fromObj.name());
        toObj.setItn(fromObj.itn());
        toObj.setAddress(address);
        toObj.setMedicines(Collections.emptyList());
        return toObj;
    }
}
