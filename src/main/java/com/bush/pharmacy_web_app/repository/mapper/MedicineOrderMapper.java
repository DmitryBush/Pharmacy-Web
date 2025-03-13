package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.MedicineOrderDto;
import com.bush.pharmacy_web_app.repository.entity.Medicine;
import org.springframework.stereotype.Component;

@Component
public class MedicineOrderMapper implements DtoMapper<Medicine, MedicineOrderDto> {
    @Override
    public MedicineOrderDto map(Medicine obj) {
        return new MedicineOrderDto(obj.getName(), obj.getManufacturer(), obj.getPrice());
    }
}
