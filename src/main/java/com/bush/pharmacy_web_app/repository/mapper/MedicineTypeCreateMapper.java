package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineType;
import org.springframework.stereotype.Component;

@Component
public class MedicineTypeCreateMapper implements DtoMapper<String, MedicineType> {

    @Override
    public MedicineType map(String obj) {
        return copyObj(obj, new MedicineType());
    }

    @Override
    public MedicineType map(String fromObj, MedicineType toObj) {
        return copyObj(fromObj, toObj);
    }

    public MedicineType copyObj(String fromObj, MedicineType toObj) {
        toObj.setType(fromObj);
        return toObj;
    }
}
