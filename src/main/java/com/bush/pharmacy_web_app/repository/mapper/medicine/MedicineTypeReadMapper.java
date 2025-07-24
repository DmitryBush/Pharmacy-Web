package com.bush.pharmacy_web_app.repository.mapper.medicine;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MedicineTypeReadMapper implements DtoMapper<MedicineType, MedicineTypeDto> {
    @Override
    public MedicineTypeDto map(MedicineType obj) {
        return Optional.ofNullable(obj.getParent())
                .map(parent -> new MedicineTypeDto(obj.getType(), parent.getType()))
                .orElseGet(() -> new MedicineTypeDto(obj.getType(), null));
    }
}
