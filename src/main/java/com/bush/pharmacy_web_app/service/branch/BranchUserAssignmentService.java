package com.bush.pharmacy_web_app.service.branch;

import com.bush.pharmacy_web_app.model.dto.user.AdminUserReadDto;
import com.bush.pharmacy_web_app.repository.branch.BranchUserAssignmentRepository;
import com.bush.pharmacy_web_app.service.user.mapper.AdminUserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchUserAssignmentService {
    private final BranchUserAssignmentRepository userAssignmentRepository;

    private final AdminUserReadMapper adminUserReadMapper;

    public List<AdminUserReadDto> findAssignedUsersByBranchId(Long id) {
        return userAssignmentRepository.findByIdBranchId(id).stream()
                .map(adminUserReadMapper::map)
                .toList();
    }
}
