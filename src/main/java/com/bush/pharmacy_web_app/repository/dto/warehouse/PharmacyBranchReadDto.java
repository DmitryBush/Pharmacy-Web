package com.bush.pharmacy_web_app.repository.dto.warehouse;

import com.bush.pharmacy_web_app.repository.dto.orders.AddressReadDto;
import com.bush.pharmacy_web_app.repository.dto.user.CustomerReadDto;
import com.bush.pharmacy_web_app.repository.dto.user.UserInfoDto;
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
