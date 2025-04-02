package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.MedicineReadDto;
import com.bush.pharmacy_web_app.repository.entity.Medicine;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.ManufacturerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineReadMapper implements DtoMapper<Medicine, MedicineReadDto> {
    private final ManufacturerReadMapper manufacturerReadMapper;
    @Override
    public MedicineReadDto map(Medicine obj) {
        var manufacturer = Optional.ofNullable(obj.getManufacturer())
                .map(manufacturerReadMapper::map)
                .orElse(null);
        return new MedicineReadDto(obj.getId(), obj.getName(), manufacturer, obj.getType(), obj.getPrice());
    }
}
