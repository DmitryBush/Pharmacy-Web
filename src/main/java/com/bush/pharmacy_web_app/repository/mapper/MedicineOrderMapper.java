package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.MedicineOrderDto;
import com.bush.pharmacy_web_app.repository.entity.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineOrderMapper implements DtoMapper<Medicine, MedicineOrderDto> {
    private final ManufacturerReadMapper manufacturerReadMapper;
    @Override
    public MedicineOrderDto map(Medicine obj) {
        var manufacturer = Optional.ofNullable(obj.getManufacturer())
                .map(manufacturerReadMapper::map)
                .orElse(null);
        return new MedicineOrderDto(obj.getName(), manufacturer, obj.getPrice());
    }
}
