package com.bush.pharmacy_web_app.service.branch.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.model.entity.branch.DayOfWeek;

public abstract class AbstractOpeningHoursHandler implements OpeningHoursHandler {
    private final DayOfWeek dayOfWeek;
    private OpeningHoursHandler nextHandler;

    public AbstractOpeningHoursHandler(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public OpeningHoursHandler setNext(OpeningHoursHandler handler) {
        nextHandler = handler;
        return nextHandler;
    }

    @Override
    public String handle(BranchOpeningHours obj) {
        if (dayOfWeek.equals(obj.getDayOfWeek()))
            return process(obj);

        if (nextHandler != null)
            return nextHandler.handle(obj);
        throw new IllegalArgumentException("There is no handler for this status: " + obj.getDayOfWeek());
    }

    protected abstract String process(BranchOpeningHours obj);
}
