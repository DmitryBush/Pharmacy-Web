package com.bush.pharmacy_web_app.repository.mapper.orders;

import com.bush.pharmacy_web_app.repository.dto.orders.AddressReadDto;
import com.bush.pharmacy_web_app.repository.dto.warehouse.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.repository.entity.PharmacyBranch;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.card.StorageItemReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PharmacyBranchReadMapper implements DtoMapper<PharmacyBranch, PharmacyBranchReadDto> {
    private final AddressReadMapper mapper;
    @Override
    public PharmacyBranchReadDto map(PharmacyBranch obj) {
        AddressReadDto address = Optional.ofNullable(obj.getAddress())
                .map(mapper::map).orElse(null);
        return new PharmacyBranchReadDto(obj.getId(), obj.getName(), address, obj.getWarehouseLimitations());
    }
}
