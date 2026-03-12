package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.branch.BranchWorkingHoursDto;
import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchCreateDto;
import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.service.address.mapper.AddressCreateMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = AddressCreateMapper.class)
public interface PharmacyBranchCreateMapper {
    @Mapping(target = "branchPhone", source = "contactPhone")
    @Mapping(target = "openingHours", source = "workingHoursDtoList")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "supervisor", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "userBranchAssigned", ignore = true)
    PharmacyBranch mapToPharmacyBranch(PharmacyBranchCreateDto createDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branch", ignore = true)
    BranchOpeningHours mapToBranchOpeningHours(BranchWorkingHoursDto workingHoursDto);

    @AfterMapping
    default void linkBranchToOpeningHours(@MappingTarget PharmacyBranch branch) {
        branch.getOpeningHours().forEach(branchOpeningHours -> branchOpeningHours.setBranch(branch));
    }
}
