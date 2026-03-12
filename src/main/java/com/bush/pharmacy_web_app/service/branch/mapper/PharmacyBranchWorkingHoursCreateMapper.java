package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchWorkingHoursDto;
import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PharmacyBranchWorkingHoursCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branch", ignore = true)
    BranchOpeningHours mapToBranchOpeningHours(PharmacyBranchWorkingHoursDto workingHoursDto);
}
