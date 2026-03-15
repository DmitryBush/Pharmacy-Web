package com.bush.pharmacy_web_app.model.dto.branch;

import com.bush.pharmacy_web_app.model.dto.address.AddressCreateDto;
import com.bush.pharmacy_web_app.validation.MobilePhone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public record PharmacyBranchCreateDto(@NotBlank @Length(min = 4, max = 32) String name,
                                      @NotNull @Positive Integer warehouseLimitations,
                                      @MobilePhone String contactPhone,
                                      @Validated AddressCreateDto address,
                                      @NotNull @Validated List<BranchWorkingHoursDto> workingHoursDtoList) {
}
