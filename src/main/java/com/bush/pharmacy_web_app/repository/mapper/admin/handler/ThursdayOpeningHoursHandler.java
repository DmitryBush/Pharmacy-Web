package com.bush.pharmacy_web_app.repository.mapper.admin.handler;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.repository.entity.branch.DayOfWeek;

public class ThursdayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public ThursdayOpeningHoursHandler() {
        super(DayOfWeek.THURSDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Чт: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
