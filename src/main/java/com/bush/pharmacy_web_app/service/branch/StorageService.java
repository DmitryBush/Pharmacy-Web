package com.bush.pharmacy_web_app.service.branch;

import com.bush.pharmacy_web_app.model.entity.branch.StorageItems;
import com.bush.pharmacy_web_app.repository.branch.PharmacyBranchRepository;
import com.bush.pharmacy_web_app.repository.branch.StorageRepository;
import com.bush.pharmacy_web_app.model.dto.warehouse.InventoryRequestDto;
import com.bush.pharmacy_web_app.model.dto.warehouse.StorageItemsReadDto;
import com.bush.pharmacy_web_app.repository.medicine.MedicineRepository;
import com.bush.pharmacy_web_app.service.branch.mapper.StorageItemReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StorageService {
    private final StorageRepository storageRepository;
    private final StorageItemReadMapper itemReadMapper;

    private final MedicineRepository medicineRepository;
    private final PharmacyBranchRepository branchRepository;

    public Page<StorageItemsReadDto> findAllItemsByBranchId(Long id, Pageable pageable) {
        return storageRepository.findByBranchId(id, pageable)
                .map(itemReadMapper::map);
    }

    public Optional<Integer> getItemQuantityByBranchId(Long branchId, Long productId) {
        return storageRepository.findAmountByBranchIdAndProductId(branchId, productId);
    }

    public List<StorageItemsReadDto> createInventoryReceiptByBranchId(InventoryRequestDto createDto) {
        return createDto.productList().stream()
                .map(storageItemCreateDto -> storageRepository
                        .findByBranchIdAndProductId(createDto.branchId(), storageItemCreateDto.medicineId())
                        .map(item -> {
                            item.setAmount(item.getAmount() + storageItemCreateDto.quantity());
                            return item;
                        })
                        .orElseGet(() -> StorageItems.builder()
                                .amount(storageItemCreateDto.quantity())
                                .product(medicineRepository.getReferenceById(storageItemCreateDto.medicineId()))
                                .branch(branchRepository.getReferenceById(createDto.branchId()))
                                .build()))
                .map(storageRepository::save)
                .map(itemReadMapper::map)
                .toList();
    }

    public List<StorageItemsReadDto> createInventorySaleByBranchId(InventoryRequestDto createDto) {
        return createDto.productList().stream()
                .map(storageItemCreateDto -> storageRepository
                        .findByBranchIdAndProductId(createDto.branchId(), storageItemCreateDto.medicineId())
                        .map(item -> {
                            if (item.getAmount() < storageItemCreateDto.quantity()) {
                                throw new IllegalArgumentException("The number of products sold cannot be greater " +
                                        "than the number of products available");
                            }
                            item.setAmount(item.getAmount() - storageItemCreateDto.quantity());
                            return item;
                        })
                        .orElseThrow(IllegalArgumentException::new))
                .map(storageRepository::save)
                .map(itemReadMapper::map)
                .toList();
    }
}
