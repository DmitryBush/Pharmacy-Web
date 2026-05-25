package com.bush.pharmacy_web_app.validation;

import com.bush.pharmacy_web_app.model.entity.user.role.RoleType;
import com.bush.pharmacy_web_app.repository.branch.BranchUserAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("SecurityValidation")
@RequiredArgsConstructor
public class SecurityValidation {
    private final BranchUserAssignmentRepository branchUserAssignmentRepository;

    public Boolean checkUserBranchAccess(UserDetails userDetails, Long branchId) {
        if (userDetails.getAuthorities().contains(RoleType.ROLE_ROOT.name())) {
            return true;
        }
        return branchUserAssignmentRepository.checkUserBranchAccess(userDetails.getUsername(), branchId);
    }
}
