package com.bush.pharmacy_web_app.repository.mapper.admin;

import com.bush.pharmacy_web_app.repository.dto.admin.MedicineAdminReadDto;
import com.bush.pharmacy_web_app.repository.entity.medicine.Medicine;
import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.manufacturer.ManufacturerReadMapper;
import com.bush.pharmacy_web_app.repository.mapper.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineAdminReadMapper implements DtoMapper<Medicine, MedicineAdminReadDto> {
    private final ManufacturerReadMapper manufacturerReadMapper;
    private final SupplierReadMapper supplierReadMapper;
    @Override
    public MedicineAdminReadDto map(Medicine obj) {
        var supplier = Optional.ofNullable(obj.getSupplier())
                .map(supplierReadMapper::map)
                .orElseThrow();
        var manufacturer = Optional.ofNullable(obj.getManufacturer())
                .map(manufacturerReadMapper::map)
                .orElseThrow();
        var type = Optional.ofNullable(obj.getType())
                .map(MedicineType::getType)
                .orElseThrow();
        return new MedicineAdminReadDto(obj.getId(), obj.getName(), supplier, manufacturer, type, obj.getPrice());
    }
}
