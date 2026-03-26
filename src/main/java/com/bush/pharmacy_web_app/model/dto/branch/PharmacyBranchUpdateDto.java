package com.bush.pharmacy_web_app.model.dto.branch;

import com.bush.pharmacy_web_app.model.dto.address.AddressCreateDto;

import java.util.List;

public record PharmacyBranchUpdateDto(String name,
                                      Integer warehouseLimitations,
                                      String contactPhone,
                                      Boolean isActive,
                                      AddressCreateDto address,
                                      List<BranchWorkingHoursDto> workingHoursDtoList) {
}
