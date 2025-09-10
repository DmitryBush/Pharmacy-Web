package com.bush.pharmacy_web_app.service.branch;

import com.bush.pharmacy_web_app.repository.branch.StorageRepository;
import com.bush.pharmacy_web_app.model.dto.warehouse.InventoryReceiptRequestDto;
import com.bush.pharmacy_web_app.model.dto.warehouse.StorageItemsReadDto;
import com.bush.pharmacy_web_app.service.branch.mapper.StorageItemReadMapper;
import com.bush.pharmacy_web_app.service.branch.mapper.InventoryReceiptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StorageService {
    private final StorageRepository storageRepository;
    private final StorageItemReadMapper itemReadMapper;

    private final InventoryReceiptMapper inventoryReceiptMapper;

    public Page<StorageItemsReadDto> findAllItemsByBranchId(Long id, Pageable pageable) {
        return storageRepository.findByBranchId(id, pageable)
                .map(itemReadMapper::map);
    }

    public Optional<Integer> getItemQuantityByBranchId(Long branchId, Long productId) {
        return storageRepository.findAmountByBranchIdAndMedicineId(branchId, productId);
    }

    @Transactional
    public List<StorageItemsReadDto> createInventoryReceiptByBranchId(InventoryReceiptRequestDto createDto) {
        return Optional.ofNullable(createDto)
                .map(inventoryReceiptMapper::map)
                .map(storageItems -> {
                    storageItems.forEach(storageRepository::saveAndFlush);
                    return storageItems;
                })
                .map(Collection::stream)
                .map(storageItems -> storageItems.map(itemReadMapper::map).toList())
                .orElse(Collections.emptyList());
    }
}
