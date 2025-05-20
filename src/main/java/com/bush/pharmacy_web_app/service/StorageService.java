package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.StorageRepository;
import com.bush.pharmacy_web_app.repository.dto.warehouse.StorageItemsReadDto;
import com.bush.pharmacy_web_app.repository.mapper.card.StorageItemReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StorageService {
    private final StorageRepository storageRepository;
    private final StorageItemReadMapper itemReadMapper;

    public Page<StorageItemsReadDto> findAllItemsByBranchId(Long id, Pageable pageable) {
        return storageRepository.findByBranchId(id, pageable)
                .map(itemReadMapper::map);
    }

    public Optional<Integer> getItemQuantityByBranchId(Long branchId, Long productId) {
        return storageRepository.findAmountByBranchIdAndMedicineId(branchId, productId);
    }
}
