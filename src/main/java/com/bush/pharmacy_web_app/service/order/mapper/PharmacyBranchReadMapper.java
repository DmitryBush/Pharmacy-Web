package com.bush.pharmacy_web_app.service.order.mapper;

import com.bush.pharmacy_web_app.model.dto.address.AddressReadDto;
import com.bush.pharmacy_web_app.model.dto.warehouse.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.branch.mapper.OpeningHoursMapper;
import com.bush.pharmacy_web_app.service.user.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PharmacyBranchReadMapper implements DtoMapper<PharmacyBranch, PharmacyBranchReadDto> {
    private final AddressReadMapper addressReadMapper;
    private final UserInfoMapper userInfoMapper;
    private final OpeningHoursMapper openingHoursMapper;
    @Override
    public PharmacyBranchReadDto map(PharmacyBranch obj) {
        AddressReadDto address = Optional.ofNullable(obj.getAddress())
                .map(addressReadMapper::map).orElse(null);
        var supervisor = Optional.ofNullable(obj.getSupervisor())
                .map(userInfoMapper::map).orElseThrow();
        var openingHours = Optional.ofNullable(obj.getOpeningHours())
                .map(openingHoursMapper::map).orElseThrow();

        return PharmacyBranchReadDto.builder()
                .id(obj.getId())
                .name(obj.getName())
                .address(address)
                .warehouseLimitations(obj.getWarehouseLimitations())
                .branchPhone(obj.getBranchPhone())
                .supervisor(supervisor)
                .openingHours(openingHours)
                .isActive(obj.getIsActive())
                .build();
    }
}
