package com.bush.pharmacy_web_app.model.dto.warehouse;

import com.bush.pharmacy_web_app.model.dto.address.AddressReadDto;
import com.bush.pharmacy_web_app.model.dto.user.UserInfoDto;
import lombok.Builder;

import java.util.List;

@Builder
public record PharmacyBranchReadDto(Long id,
                                    String name,
                                    AddressReadDto address,
                                    Integer warehouseLimitations,
                                    Boolean isActive,
                                    String branchPhone,
                                    UserInfoDto supervisor,
                                    List<String> openingHours) {
}
