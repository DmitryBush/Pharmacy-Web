package com.bush.pharmacy_web_app.repository.mapper.card;

import com.bush.pharmacy_web_app.repository.dto.card.StorageItemsReadDto;
import com.bush.pharmacy_web_app.repository.entity.StorageItems;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.orders.MedicineReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StorageItemReadMapper implements DtoMapper<StorageItems, StorageItemsReadDto> {
    private final MedicineReadMapper medicineReadMapper;
    @Override
    public StorageItemsReadDto map(StorageItems obj) {
        var medicine = Optional.ofNullable(obj.getMedicine())
                .map(medicineReadMapper::map).orElse(null);
        return new StorageItemsReadDto(obj.getId(), obj.getAmount(), medicine);
    }
}
