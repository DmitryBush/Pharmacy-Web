package com.bush.pharmacy_web_app.repository.mapper.warehouse;

import com.bush.pharmacy_web_app.repository.MedicineRepository;
import com.bush.pharmacy_web_app.repository.PharmacyBranchRepository;
import com.bush.pharmacy_web_app.repository.StorageRepository;
import com.bush.pharmacy_web_app.repository.dto.warehouse.InventoryReceiptRequestDto;
import com.bush.pharmacy_web_app.repository.dto.warehouse.StorageItemCreateDto;
import com.bush.pharmacy_web_app.repository.entity.StorageItems;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventoryReceiptMapper implements DtoMapper<InventoryReceiptRequestDto, List<StorageItems>> {
    private Long branchId;

    private final StorageRepository storageRepository;
    private final MedicineRepository medicineRepository;
    private final PharmacyBranchRepository branchRepository;
    @Override
    public List<StorageItems> map(InventoryReceiptRequestDto obj) {
        branchId = obj.branchId();
        return obj.productList().stream()
                .map(storageItemCreateDto -> copyObj(storageItemCreateDto, new StorageItems()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private StorageItems copyObj(StorageItemCreateDto fromObj, StorageItems toObj) {
        return Optional.ofNullable(storageRepository.findByBranchIdAndMedicineId(branchId, fromObj.medicineId()))
                .map(item -> {
                    item.setAmount(item.getAmount() + fromObj.quantity());
                    return item;
                })
                .orElseGet(() -> {
                    var medicine = medicineRepository.findById(fromObj.medicineId())
                            .orElseThrow();
                    var branch = branchRepository.findById(branchId)
                            .orElseThrow();

                    toObj.setAmount(fromObj.quantity());
                    toObj.setMedicine(medicine);
                    toObj.setBranch(branch);
                    return toObj;
                });
    }
}
