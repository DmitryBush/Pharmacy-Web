package com.bush.pharmacy_web_app.service.branch.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.model.entity.branch.DayOfWeek;

public class WednesdayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public WednesdayOpeningHoursHandler() {
        super(DayOfWeek.WEDNESDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Ср: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
