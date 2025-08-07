package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.warehouse.StorageItemsReadDto;
import com.bush.pharmacy_web_app.model.entity.branch.StorageItems;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicinePreviewReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StorageItemReadMapper implements DtoMapper<StorageItems, StorageItemsReadDto> {
    private final MedicinePreviewReadMapper medicinePreviewReadMapper;
    @Override
    public StorageItemsReadDto map(StorageItems obj) {
        var medicine = Optional.ofNullable(obj.getMedicine())
                .map(medicinePreviewReadMapper::map).orElse(null);
        return new StorageItemsReadDto(obj.getId(), obj.getAmount(), medicine);
    }
}
