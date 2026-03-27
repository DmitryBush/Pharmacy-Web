package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.dto.branch.BranchWorkingHoursDto;
import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.model.entity.branch.DayOfWeek;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BranchWorkingHoursCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branch", ignore = true)
    BranchOpeningHours mapToBranchOpeningHours(BranchWorkingHoursDto workingHoursDto);

    default BranchOpeningHours updateBranchOpeningHours(BranchOpeningHours openingHours,
                                                        Map<DayOfWeek, BranchWorkingHoursDto> updateMap) {
        if (updateMap.containsKey(openingHours.getDayOfWeek())) {
            return updateBranchOpeningHours(openingHours, updateMap.get(openingHours.getDayOfWeek()));
        }
        return openingHours;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branch", ignore = true)
    BranchOpeningHours updateBranchOpeningHours(@MappingTarget BranchOpeningHours openingHours, BranchWorkingHoursDto dto);
}
