package com.bush.pharmacy_web_app.repository.branch;

import com.bush.pharmacy_web_app.model.entity.branch.BranchOpeningHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchWorkingHoursRepository extends JpaRepository<BranchOpeningHours, Long> {
    List<BranchOpeningHours> findByBranchId(Long branchId);
}
