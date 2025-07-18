package com.bush.pharmacy_web_app.validation;

import com.bush.pharmacy_web_app.repository.BranchUserAssignmentRepository;
import com.bush.pharmacy_web_app.repository.UserRepository;
import com.bush.pharmacy_web_app.repository.dto.warehouse.InventoryReceiptRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SecurityValidation")
@RequiredArgsConstructor
public class SecurityValidation {
    private final BranchUserAssignmentRepository branchUserAssignmentRepository;

    public Boolean checkUserBranchAccess(UserDetails userDetails, Long branchId) {
        return branchUserAssignmentRepository.checkUserBranchAccess(userDetails.getUsername(), branchId);
    }
}
