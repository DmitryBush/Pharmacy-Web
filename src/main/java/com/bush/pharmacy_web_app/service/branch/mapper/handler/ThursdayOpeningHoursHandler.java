package com.bush.pharmacy_web_app.service.branch.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.model.entity.branch.DayOfWeek;

public class ThursdayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public ThursdayOpeningHoursHandler() {
        super(DayOfWeek.THURSDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Чт: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
