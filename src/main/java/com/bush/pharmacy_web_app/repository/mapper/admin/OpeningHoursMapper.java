package com.bush.pharmacy_web_app.repository.mapper.admin;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.admin.handler.AbstractOpeningHoursHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpeningHoursMapper implements DtoMapper<List<BranchOpeningHours>, List<String>> {
    @Autowired
    private final AbstractOpeningHoursHandler abstractOpeningHoursHandler;
    @Override
    public List<String> map(List<BranchOpeningHours> obj) {
        return obj.stream()
                .sorted(Comparator.comparing(BranchOpeningHours::getDayOfWeek))
                .map(abstractOpeningHoursHandler::handle)
                .toList();
    }
}
