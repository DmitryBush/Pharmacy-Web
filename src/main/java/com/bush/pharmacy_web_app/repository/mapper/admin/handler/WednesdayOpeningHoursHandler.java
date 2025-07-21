package com.bush.pharmacy_web_app.repository.mapper.admin.handler;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.repository.entity.branch.DayOfWeek;

public class WednesdayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public WednesdayOpeningHoursHandler() {
        super(DayOfWeek.WEDNESDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Ср: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
