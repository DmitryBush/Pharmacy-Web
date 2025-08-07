package com.bush.pharmacy_web_app.service.branch.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.model.entity.branch.DayOfWeek;

public class FridayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public FridayOpeningHoursHandler() {
        super(DayOfWeek.FRIDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Пт: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
