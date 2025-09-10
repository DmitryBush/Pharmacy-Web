package com.bush.pharmacy_web_app.service.branch.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.model.entity.branch.DayOfWeek;

public class MondayOpeningHoursHandler extends AbstractOpeningHoursHandler {
    public MondayOpeningHoursHandler() {
        super(DayOfWeek.MONDAY);
    }

    @Override
    protected String process(BranchOpeningHours obj) {
        return String.format("Пн: %s-%s", obj.getOpenTime(), obj.getCloseTime());
    }
}
