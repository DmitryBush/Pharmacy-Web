package com.bush.pharmacy_web_app.service.branch.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.model.entity.branch.DayOfWeek;

public class SundayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public SundayOpeningHoursHandler() {
        super(DayOfWeek.SUNDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Вс: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
