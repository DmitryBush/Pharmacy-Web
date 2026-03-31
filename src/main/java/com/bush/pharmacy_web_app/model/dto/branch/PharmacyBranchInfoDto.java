package com.bush.pharmacy_web_app.model.dto.branch;

import com.bush.pharmacy_web_app.model.dto.address.AddressReadDto;
import com.bush.pharmacy_web_app.model.dto.user.UserInfoDto;

import java.util.List;

public record PharmacyBranchInfoDto(Long id,
                                    String name,
                                    AddressReadDto address,
                                    Integer warehouseLimitations,
                                    Boolean isActive,
                                    String branchPhone,
                                    UserInfoDto supervisor,
                                    List<BranchWorkingHoursDto> workingHoursList) {
}
