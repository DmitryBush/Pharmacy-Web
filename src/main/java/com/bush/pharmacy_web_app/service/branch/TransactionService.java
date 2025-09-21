package com.bush.pharmacy_web_app.service.branch;

import com.bush.pharmacy_web_app.model.dto.warehouse.InventoryRequestDto;
import com.bush.pharmacy_web_app.model.dto.warehouse.StorageItemCreateDto;
import com.bush.pharmacy_web_app.model.dto.warehouse.StorageItemsReadDto;
import com.bush.pharmacy_web_app.model.dto.warehouse.TransactionCreateDto;
import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionName;
import com.bush.pharmacy_web_app.repository.branch.PharmacyBranchRepository;
import com.bush.pharmacy_web_app.repository.branch.TransactionHistoryRepository;
import com.bush.pharmacy_web_app.repository.branch.TransactionTypeRepository;
import com.bush.pharmacy_web_app.repository.medicine.MedicineRepository;
import com.bush.pharmacy_web_app.repository.order.OrderRepository;
import com.bush.pharmacy_web_app.service.branch.mapper.TransactionCreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {
    private final TransactionHistoryRepository transactionRepository;

    private final TransactionCreateMapper transactionCreateMapper;

    private final OrderRepository orderRepository;
    private final PharmacyBranchRepository branchRepository;
    private final TransactionTypeRepository typeRepository;
    private final MedicineRepository medicineRepository;

    private final StorageService storageService;

    @Transactional
    public List<StorageItemsReadDto> createTransaction(TransactionCreateDto createDto) {
        var order = Optional.ofNullable(createDto.orderId())
                .map(orderRepository::getReferenceById)
                .orElse(null);
        var branch = Optional.ofNullable(createDto.branchId())
                .map(branchRepository::getReferenceById)
                .orElseThrow(IllegalArgumentException::new);
        var transactionType = Optional.ofNullable(createDto.transactionType())
                .map(TransactionName::ordinal)
                .map(typeRepository::getReferenceById)
                .orElseThrow(IllegalArgumentException::new);
        var transactionItems = Optional.ofNullable(createDto.transactionItemsList())
                .map(transactionItemsList -> transactionItemsList.stream()
                        .map(StorageItemCreateDto::medicineId)
                        .map(medicineRepository::getReferenceById)
                        .toList())
                .orElseThrow(IllegalArgumentException::new);
        Optional.of(createDto)
                .map(transactionCreateMapper::map)
                .map(transaction -> {
                    transaction.setType(transactionType);
                    transaction.setBranch(branch);
                    transaction.setOrder(order);
                    transaction.setItems(transactionItems);
                    return transaction;
                })
                .map(transactionRepository::save);

        InventoryRequestDto inventoryRequestDto = new InventoryRequestDto(createDto.branchId(),
                createDto.transactionItemsList());
        if (createDto.transactionType().equals(TransactionName.RECEIVING)) {
            return storageService.createInventoryReceiptByBranchId(inventoryRequestDto);
        } else if (createDto.transactionType().equals(TransactionName.SALE)) {
            return storageService.createInventorySaleByBranchId(inventoryRequestDto);
        }
        throw new IllegalArgumentException("Unknown transaction type");
    }
}
