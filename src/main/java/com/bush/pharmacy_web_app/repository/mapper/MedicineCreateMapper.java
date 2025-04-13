package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.MedicineRepository;
import com.bush.pharmacy_web_app.repository.TypeRepository;
import com.bush.pharmacy_web_app.repository.dto.catalog.MedicineCreateDto;
import com.bush.pharmacy_web_app.repository.entity.medicine.Medicine;
import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.repository.mapper.manufacturer.ManufacturerCreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineCreateMapper implements DtoMapper<MedicineCreateDto, Medicine>{
    private final SupplierCreateMapper supplierCreateMapper;
    private final ManufacturerCreateMapper manufacturerCreateMapper;
    private final MedicineTypeCreateMapper typeCreateMapper;
    private final TypeRepository typeRepository;
    private final MedicineRepository medicineRepository;
    @Override
    public Medicine map(MedicineCreateDto obj) {
        return copyObj(obj, new Medicine());
    }

    @Override
    public Medicine map(MedicineCreateDto fromObj, Medicine toObj) {
        return copyObj(fromObj, toObj);
    }

    private Medicine copyObj(MedicineCreateDto fromObj, Medicine toObj) {
        return Optional.ofNullable(fromObj.id())
                .flatMap(medicineRepository::findById)
                .orElseGet(() -> {
                    var supplier = Optional.ofNullable(fromObj.supplier())
                            .map(supplierCreateMapper::map)
                            .orElseThrow();
                    var manufacturer = Optional.ofNullable(fromObj.manufacturer())
                            .map(manufacturerCreateMapper::map)
                            .orElseThrow();
                    var medicineType = Optional.ofNullable(fromObj.type())
                            .map(type -> typeRepository.findByType(type)
                                    .orElseGet(() -> typeCreateMapper.map(type)))
                            .orElseThrow();
                    toObj.setName(fromObj.name());
                    toObj.setType(medicineType);
                    toObj.setManufacturer(manufacturer);
                    toObj.setPrice(fromObj.price());
                    toObj.setRecipe(fromObj.recipe());
                    toObj.setSupplier(supplier);
                    return toObj;
                });
//        var supplier = Optional.ofNullable(fromObj.supplier())
//                        .map(supplierCreateMapper::map)
//                                .orElseThrow();
//        var manufacturer = Optional.ofNullable(fromObj.manufacturer())
//                .map(manufacturerCreateMapper::map)
//                .orElseThrow();
//        var medicineType = Optional.ofNullable(fromObj.type())
//                        .map(type -> typeRepository.findByType(type)
//                                .orElseGet(() -> typeCreateMapper.map(type)))
//                        .orElseThrow();
//        toObj.setName(fromObj.name());
//        toObj.setType(medicineType);
//        toObj.setManufacturer(manufacturer);
//        toObj.setPrice(fromObj.price());
//        toObj.setRecipe(fromObj.recipe());
//        toObj.setSupplier(supplier);
//        return toObj;
    }
}
