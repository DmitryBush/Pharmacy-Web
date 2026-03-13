package com.bush.pharmacy_web_app.service.branch;

import com.bush.pharmacy_web_app.repository.branch.BranchWorkingHoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchWorkingHoursService {
    private final BranchWorkingHoursRepository branchWorkingHoursRepository;

}
