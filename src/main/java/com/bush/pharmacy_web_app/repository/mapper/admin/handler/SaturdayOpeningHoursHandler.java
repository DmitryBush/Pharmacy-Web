package com.bush.pharmacy_web_app.repository.mapper.admin.handler;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.repository.entity.branch.DayOfWeek;

public class SaturdayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public SaturdayOpeningHoursHandler() {
        super(DayOfWeek.SATURDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Сб: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
