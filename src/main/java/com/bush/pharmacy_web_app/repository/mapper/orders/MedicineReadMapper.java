package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineReadDto;
import com.bush.pharmacy_web_app.repository.entity.medicine.Medicine;
import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.manufacturer.ManufacturerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineReadMapper implements DtoMapper<Medicine, MedicineReadDto> {
    private final ManufacturerReadMapper manufacturerReadMapper;
    @Override
    public MedicineReadDto map(Medicine obj) {
        var type = Optional.ofNullable(obj.getType())
                .map(MedicineType::getType)
                .orElse(null);
        var manufacturer = Optional.ofNullable(obj.getManufacturer())
                .map(manufacturerReadMapper::map)
                .orElse(null);
        return new MedicineReadDto(obj.getId(), obj.getName(), manufacturer, type, obj.getPrice());
    }
}
