package com.bush.pharmacy_web_app.service.branch;

import com.bush.pharmacy_web_app.model.dto.branch.BranchWorkingHoursDto;
import com.bush.pharmacy_web_app.model.entity.branch.DayOfWeek;
import com.bush.pharmacy_web_app.repository.branch.BranchWorkingHoursRepository;
import com.bush.pharmacy_web_app.service.branch.mapper.BranchWorkingHoursCreateMapper;
import com.bush.pharmacy_web_app.service.branch.mapper.BranchWorkingHoursReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchWorkingHoursService {
    private final BranchWorkingHoursRepository branchWorkingHoursRepository;

    private final BranchWorkingHoursCreateMapper workingHoursCreateMapper;
    private final BranchWorkingHoursReadMapper workingHoursReadMapper;

    public List<BranchWorkingHoursDto> findWorkingHoursByBranchId(Long branchId) {
        return branchWorkingHoursRepository.findByBranchId(branchId).stream()
                .map(workingHoursReadMapper::mapToBranchWorkingHoursDto)
                .toList();
    }

    public List<BranchWorkingHoursDto> updateWorkingHoursByBranchId(Long branchId, List<BranchWorkingHoursDto> hoursDtoList) {
        Map<DayOfWeek, BranchWorkingHoursDto> updateMap = hoursDtoList.stream()
                .collect(Collectors.toMap(BranchWorkingHoursDto::dayOfWeek, Function.identity(),
                        (ex, rep) -> ex));
        return branchWorkingHoursRepository.findByBranchId(branchId).stream()
                .map(workingHours ->
                        workingHoursCreateMapper.updateBranchOpeningHours(workingHours, updateMap))
                .map(branchWorkingHoursRepository::saveAndFlush)
                .map(workingHoursReadMapper::mapToBranchWorkingHoursDto)
                .toList();
    }
}
