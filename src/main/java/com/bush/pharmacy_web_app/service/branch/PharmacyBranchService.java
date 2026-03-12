package com.bush.pharmacy_web_app.service.branch;

import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchCreateDto;
import com.bush.pharmacy_web_app.repository.branch.PharmacyBranchRepository;
import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.service.branch.mapper.PharmacyBranchCreateMapper;
import com.bush.pharmacy_web_app.service.order.mapper.PharmacyBranchReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PharmacyBranchService {
    private final PharmacyBranchRepository branchRepository;

    private final PharmacyBranchCreateMapper branchCreateMapper;
    private final PharmacyBranchReadMapper branchReadMapper;

    public Optional<PharmacyBranchReadDto> findByBranchId(Long id) {
        return branchRepository.findById(id)
                .map(branchReadMapper::map);
    }

    public List<PharmacyBranchReadDto> findBranchesWithMedicineLocated(Long productId) {
        return branchRepository.findBranchesWithMedicineLocated(productId)
                .stream()
                .map(branchReadMapper::map)
                .toList();
    }

    public List<PharmacyBranchReadDto> findUserAssignedBranches(String userId) {
        return branchRepository.findUserAssignedBranches(userId)
                .stream()
                .map(branchReadMapper::map)
                .toList();
    }

    public Page<PharmacyBranchReadDto> findPharmacyBranches(Pageable pageable) {
        return branchRepository.findAll(pageable)
                .map(branchReadMapper::map);
    }

    @Transactional
    public PharmacyBranchReadDto createBranch(PharmacyBranchCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(branchCreateMapper::mapToPharmacyBranch)
                .map(branchRepository::save)
                .map(branchReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
}
