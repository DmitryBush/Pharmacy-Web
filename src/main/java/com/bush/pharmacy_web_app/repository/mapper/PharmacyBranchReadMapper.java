package com.bush.pharmacy_web_app.repository.mapper;

import com.bush.pharmacy_web_app.repository.dto.AddressReadDto;
import com.bush.pharmacy_web_app.repository.dto.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.repository.entity.PharmacyBranch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PharmacyBranchReadMapper implements DtoMapper<PharmacyBranch, PharmacyBranchReadDto> {
    private final AddressReadMapper mapper;
    @Override
    public PharmacyBranchReadDto map(PharmacyBranch obj) {
        AddressReadDto address = Optional.ofNullable(obj.getAddress())
                .map(mapper::map).orElse(null);
        return new PharmacyBranchReadDto(address);
    }
}
