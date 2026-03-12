package com.bush.pharmacy_web_app.model.dto.branch;

import com.bush.pharmacy_web_app.model.entity.branch.DayOfWeek;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record PharmacyBranchWorkingHoursDto(@NotNull DayOfWeek dayOfWeek,
                                            @NotNull LocalTime openTime,
                                            @NotNull LocalTime closeTime,
                                            @NotNull Boolean dayOff,
                                            @NotNull Boolean aroundClock) {
}
