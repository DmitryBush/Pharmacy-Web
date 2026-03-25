package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchInfoDto;
import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.service.address.mapper.AddressReadMapper;
import com.bush.pharmacy_web_app.service.user.mapper.UserInfoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressReadMapper.class, UserInfoMapper.class, OpeningHoursMapper.class})
public interface PharmacyBranchReadMapper {
    PharmacyBranchReadDto mapToPharmacyBranchReadDto(PharmacyBranch pharmacyBranch);
    @Mapping(target = "supervisor", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "workingHoursList", source = "openingHours")
    PharmacyBranchInfoDto mapToPharmacyBranchInfoDto(PharmacyBranch pharmacyBranch);
}
