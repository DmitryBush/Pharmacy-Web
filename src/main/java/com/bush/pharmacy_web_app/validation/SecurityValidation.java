package com.bush.pharmacy_web_app.validation;

import com.bush.pharmacy_web_app.repository.branch.BranchUserAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("SecurityValidation")
@RequiredArgsConstructor
public class SecurityValidation {
    private final BranchUserAssignmentRepository branchUserAssignmentRepository;

    public Boolean checkUserBranchAccess(UserDetails userDetails, Long branchId) {
        return branchUserAssignmentRepository.checkUserBranchAccess(userDetails.getUsername(), branchId);
    }
}
