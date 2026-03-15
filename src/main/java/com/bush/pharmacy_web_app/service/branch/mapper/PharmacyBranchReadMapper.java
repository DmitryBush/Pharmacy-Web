package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.service.address.mapper.AddressReadMapper;
import com.bush.pharmacy_web_app.service.user.mapper.UserInfoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressReadMapper.class, UserInfoMapper.class, OpeningHoursMapper.class})
public interface PharmacyBranchReadMapper {
    PharmacyBranchReadDto mapToPharmacyBranchReadDto(PharmacyBranch pharmacyBranch);
}
