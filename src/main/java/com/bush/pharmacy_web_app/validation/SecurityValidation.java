package com.bush.pharmacy_web_app.validation;

import com.bush.pharmacy_web_app.model.entity.user.role.RoleType;
import com.bush.pharmacy_web_app.repository.branch.BranchUserAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("SecurityValidation")
@RequiredArgsConstructor
public class SecurityValidation {
    private final BranchUserAssignmentRepository branchUserAssignmentRepository;

    public Boolean checkUserBranchAccess(Long branchId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return false;
        }
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(RoleType.ROLE_ROOT.name()))) {
            return true;
        }
        UserDetails details = (UserDetails) authentication.getPrincipal();
        return branchUserAssignmentRepository.checkUserBranchAccess(details.getUsername(), branchId);
    }
}
