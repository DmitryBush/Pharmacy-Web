package com.bush.pharmacy_web_app.service.branch.mapper;

import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.branch.mapper.handler.AbstractOpeningHoursHandler;
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
