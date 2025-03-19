package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.catalog.MedicineCreateDto;
import com.bush.pharmacy_web_app.repository.entity.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineCreateMapper implements DtoMapper<MedicineCreateDto, Medicine>{
    private final SupplierCreateMapper createMapper;
    @Override
    public Medicine map(MedicineCreateDto obj) {
        return copyObj(obj, new Medicine());
    }

    @Override
    public Medicine map(MedicineCreateDto fromObj, Medicine toObj) {
        return copyObj(fromObj, toObj);
    }

    private Medicine copyObj(MedicineCreateDto fromObj, Medicine toObj) {
        var supplier = Optional.ofNullable(fromObj.supplier())
                        .map(createMapper::map)
                                .orElseThrow();
        toObj.setName(fromObj.name());
        toObj.setType(fromObj.type());
        toObj.setManufacturer(fromObj.manufacturer());
        toObj.setPrice(fromObj.price());
        toObj.setRecipe(fromObj.recipe());
        toObj.setSupplier(supplier);
        return toObj;
    }
}
