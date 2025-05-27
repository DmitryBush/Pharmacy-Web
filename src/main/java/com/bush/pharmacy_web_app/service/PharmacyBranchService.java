package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.PharmacyBranchRepository;
import com.bush.pharmacy_web_app.repository.dto.warehouse.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.repository.mapper.orders.PharmacyBranchReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PharmacyBranchService {
    private final PharmacyBranchRepository branchRepository;
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
}
