package com.bush.pharmacy_web_app.service.medicine.mapper;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MedicineTypeReadMapper implements DtoMapper<MedicineType, MedicineTypeDto> {
    @Override
    public MedicineTypeDto map(MedicineType obj) {
        return Optional.ofNullable(obj.getParent())
                .map(parent -> new MedicineTypeDto(obj.getId(), obj.getType(), parent.getType()))
                .orElseGet(() -> new MedicineTypeDto(obj.getId(), obj.getType(), null));
    }
}
