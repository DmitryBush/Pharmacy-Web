package com.bush.pharmacy_web_app.service.branch;

import com.bush.pharmacy_web_app.model.dto.user.AdminUserReadDto;
import com.bush.pharmacy_web_app.model.entity.branch.BranchUserAssignment;
import com.bush.pharmacy_web_app.model.entity.branch.BranchUserAssignmentId;
import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.model.entity.user.User;
import com.bush.pharmacy_web_app.repository.branch.BranchUserAssignmentRepository;
import com.bush.pharmacy_web_app.service.user.UserService;
import com.bush.pharmacy_web_app.service.user.mapper.AdminUserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchUserAssignmentService {
    private final BranchUserAssignmentRepository userAssignmentRepository;

    private final AdminUserReadMapper adminUserReadMapper;

    private final UserService userService;
    private final PharmacyBranchService branchService;

    public List<AdminUserReadDto> findAssignedUsersByBranchId(Long id) {
        return userAssignmentRepository.findAssignedUsersByBranchId(id).stream()
                .map(adminUserReadMapper::map)
                .toList();
    }

    @Transactional
    public AdminUserReadDto assignUserToBranch(Long branchId, String userId) {
        final User user = userService.getUserReferenceById(userId);
        final PharmacyBranch branch = branchService.getReferenceById(branchId);
        BranchUserAssignment assignment = new BranchUserAssignment(new BranchUserAssignmentId(user, branch), LocalDateTime.now());
        return Optional.of(assignment)
                .map(userAssignmentRepository::save)
                .map(BranchUserAssignment::getId)
                .map(BranchUserAssignmentId::getUser)
                .map(adminUserReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public void unlinkUserFromBranch(Long branchId, String userId) {
        userAssignmentRepository.findAssignmentByUserIdAndBranchId(branchId, userId)
                .ifPresentOrElse(userAssignmentRepository::delete,
                        () -> {throw new ResponseStatusException(HttpStatus.NOT_FOUND);});
    }
}
