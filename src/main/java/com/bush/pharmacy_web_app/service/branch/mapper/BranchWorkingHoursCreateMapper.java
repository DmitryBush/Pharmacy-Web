package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.branch.BranchWorkingHoursDto;
import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BranchWorkingHoursCreateMapper {
    @Mapping(target = "id", ignore = true)
    BranchOpeningHours mapToBranchOpeningHours(BranchWorkingHoursDto workingHoursDto, PharmacyBranch branch);
}
