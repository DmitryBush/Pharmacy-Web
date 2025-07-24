package com.bush.pharmacy_web_app.repository.mapper.admin.handler;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.repository.entity.branch.DayOfWeek;

public class TuesdayOpeningHoursHandler extends AbstractOpeningHoursHandler{
    public TuesdayOpeningHoursHandler() {
        super(DayOfWeek.TUESDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Вт: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
