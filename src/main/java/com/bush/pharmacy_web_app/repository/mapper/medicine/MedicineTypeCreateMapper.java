package com.bush.pharmacy_web_app.repository.mapper.medicine;

import com.bush.pharmacy_web_app.repository.MedicineTypeRepository;
import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class MedicineTypeCreateMapper implements DtoMapper<MedicineTypeDto, MedicineType> {
    private final MedicineTypeRepository medicineTypeRepository;
    @Override
    public MedicineType map(MedicineTypeDto obj) {
        return copyObj(obj, new MedicineType());
    }

    @Override
    public MedicineType map(MedicineTypeDto fromObj, MedicineType toObj) {
        return copyObj(fromObj, toObj);
    }

    private MedicineType copyObj(MedicineTypeDto fromObj, MedicineType toObj) {
        return medicineTypeRepository.findByType(fromObj.name())
                .orElseGet(() -> {
                    var parent = medicineTypeRepository.findByType(fromObj.parent())
                            .orElseThrow();

                    toObj.setType(fromObj.name());
                    toObj.setParent(parent);
                    toObj.setChildTypes(Collections.emptyList());
                    return toObj;
                });
    }
}
