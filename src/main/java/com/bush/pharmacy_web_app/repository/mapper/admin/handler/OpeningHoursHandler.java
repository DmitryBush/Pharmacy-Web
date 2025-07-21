package com.bush.pharmacy_web_app.repository.mapper.admin.handler;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchOpeningHours;

public interface OpeningHoursHandler {
    OpeningHoursHandler setNext(OpeningHoursHandler handler);
    String handle(BranchOpeningHours obj);
}
