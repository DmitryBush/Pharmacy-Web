package com.bush.pharmacy_web_app.repository.mapper.admin.handler;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.repository.entity.branch.DayOfWeek;

public class SundayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public SundayOpeningHoursHandler() {
        super(DayOfWeek.SUNDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Вс: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
