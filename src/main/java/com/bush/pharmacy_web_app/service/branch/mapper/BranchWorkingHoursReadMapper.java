package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.branch.BranchWorkingHoursDto;
import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BranchWorkingHoursReadMapper {
    BranchWorkingHoursDto mapToBranchWorkingHoursDto(BranchOpeningHours openingHours);
}
