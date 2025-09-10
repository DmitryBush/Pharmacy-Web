package com.bush.pharmacy_web_app.service.medicine.mapper;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.repository.medicine.MedicineTypeRepository;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        var parent = Optional.ofNullable(fromObj.parent())
                .map(medicineTypeRepository::findByType)
                .map(Optional::orElseThrow)
                .orElse(null);

        toObj.setType(fromObj.name());
        toObj.setParent(parent);
        return toObj;
    }
}
