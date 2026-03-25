package com.bush.pharmacy_web_app.service.branch;

import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchCreateDto;
import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchInfoDto;
import com.bush.pharmacy_web_app.repository.branch.PharmacyBranchRepository;
import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.service.branch.mapper.PharmacyBranchCreateMapper;
import com.bush.pharmacy_web_app.service.branch.mapper.PharmacyBranchReadMapper;
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

    public PharmacyBranchInfoDto findBranchInfoById(Long id) {
        return branchRepository.findBranchInfoById(id)
                .map(branchReadMapper::mapToPharmacyBranchInfoDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<PharmacyBranchReadDto> findBranchesWithMedicineLocated(Long productId) {
        return branchRepository.findBranchesWithMedicineLocated(productId)
                .stream()
                .map(branchReadMapper::mapToPharmacyBranchReadDto)
                .toList();
    }

    public List<PharmacyBranchReadDto> findUserAssignedBranches(String userId) {
        return branchRepository.findUserAssignedBranches(userId)
                .stream()
                .map(branchReadMapper::mapToPharmacyBranchReadDto)
                .toList();
    }

    public Page<PharmacyBranchReadDto> findPharmacyBranches(Pageable pageable) {
        return branchRepository.findAll(pageable)
                .map(branchReadMapper::mapToPharmacyBranchReadDto);
    }

    @Transactional
    public PharmacyBranchReadDto createBranch(PharmacyBranchCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(branchCreateMapper::mapToPharmacyBranch)
                .map(branchRepository::save)
                .map(branchReadMapper::mapToPharmacyBranchReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
}
