package com.bush.pharmacy_web_app.service.supplier.mapper;

import com.bush.pharmacy_web_app.model.dto.supplier.SupplierCreateDto;
import com.bush.pharmacy_web_app.model.entity.Supplier;
import com.bush.pharmacy_web_app.repository.supplier.SupplierRepository;
import com.bush.pharmacy_web_app.shared.mapper.AddressCreateMapper;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SupplierCreateMapper implements DtoMapper<SupplierCreateDto, Supplier> {
    private final AddressCreateMapper createMapper;
    private final SupplierRepository supplierRepository;
    @Override
    public Supplier map(SupplierCreateDto obj) {
        return copyObj(obj, new Supplier());
    }

    @Override
    public Supplier map(SupplierCreateDto fromObj, Supplier toObj) {
        return copyObj(fromObj, toObj);
    }

    private Supplier copyObj(SupplierCreateDto fromObj, Supplier toObj) {
        return supplierRepository.findById(fromObj.itn())
                .orElseGet(() -> {
                    var address = Optional.ofNullable(fromObj.address())
                            .map(createMapper::map)
                            .orElseThrow();
                    toObj.setName(fromObj.name());
                    toObj.setItn(fromObj.itn());
                    toObj.setAddress(address);
                    toObj.setProducts(Collections.emptyList());
                    return toObj;
                });
    }
}
