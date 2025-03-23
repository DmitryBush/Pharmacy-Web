package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.MedicineReadDto;
import com.bush.pharmacy_web_app.repository.entity.Medicine;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class MedicineReadMapper implements DtoMapper<Medicine, MedicineReadDto> {
    @Override
    public MedicineReadDto map(Medicine obj) {
        return new MedicineReadDto(obj.getId(), obj.getName(), obj.getManufacturer(), obj.getType(), obj.getPrice());
    }
}
