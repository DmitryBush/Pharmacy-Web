package com.bush.pharmacy_web_app.repository.mapper.admin.handler;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.repository.entity.branch.DayOfWeek;

public class FridayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public FridayOpeningHoursHandler() {
        super(DayOfWeek.FRIDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Пт: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
