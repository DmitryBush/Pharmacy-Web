package com.bush.pharmacy_web_app.repository.mapper.admin.handler;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.repository.entity.branch.DayOfWeek;

public class MondayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public MondayOpeningHoursHandler() {
        super(DayOfWeek.MONDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Пн: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
