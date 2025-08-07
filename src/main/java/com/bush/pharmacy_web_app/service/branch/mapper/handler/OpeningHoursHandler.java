package com.bush.pharmacy_web_app.service.branch.mapper.handler;

import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;

public interface OpeningHoursHandler {
    OpeningHoursHandler setNext(OpeningHoursHandler handler);
    String handle(BranchOpeningHours obj);
}
