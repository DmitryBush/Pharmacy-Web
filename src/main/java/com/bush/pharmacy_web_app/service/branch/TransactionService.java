package com.bush.pharmacy_web_app.service.branch;

import com.bush.pharmacy_web_app.model.dto.warehouse.*;
import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionName;
import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionType;
import com.bush.pharmacy_web_app.repository.branch.PharmacyBranchRepository;
import com.bush.pharmacy_web_app.repository.branch.TransactionHistoryRepository;
import com.bush.pharmacy_web_app.repository.branch.TransactionTypeRepository;
import com.bush.pharmacy_web_app.repository.medicine.MedicineRepository;
import com.bush.pharmacy_web_app.repository.order.OrderRepository;
import com.bush.pharmacy_web_app.service.branch.mapper.TransactionCreateMapper;
import com.bush.pharmacy_web_app.service.branch.mapper.TransactionReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final TransactionReadMapper transactionReadMapper;

    private final OrderRepository orderRepository;
    private final PharmacyBranchRepository branchRepository;
    private final TransactionTypeRepository typeRepository;
    private final MedicineRepository medicineRepository;

    private final StorageService storageService;

    public List<TransactionReadDto> findAllTransactionsByBranchId(Long branchId) {
        return transactionRepository.findByBranchId(branchId).stream()
                .map(transactionReadMapper::map)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR') and " +
            "@SecurityValidation.checkUserBranchAccess(#userDetails, #transactionInfo.branchId)")
    @Transactional
    public List<StorageItemsReadDto> createReceiptTransaction(UserDetails userDetails,
                                                              TransactionCreateDto transactionInfo) {
        var transactionType = typeRepository.getReferenceById(TransactionName.RECEIVING.ordinal());
        createTransaction(transactionInfo, transactionType);

        var inventoryRequestDto = new InventoryRequestDto(transactionInfo.branchId(), transactionInfo.transactionItemsList());
        return storageService.createInventoryReceiptByBranchId(inventoryRequestDto);
    }

    private void createTransaction(TransactionCreateDto transactionInfo, TransactionType transactionType) {
        var order = Optional.ofNullable(transactionInfo.orderId())
                .map(orderRepository::getReferenceById)
                .orElse(null);
        var branch = Optional.ofNullable(transactionInfo.branchId())
                .map(branchRepository::getReferenceById)
                .orElseThrow(IllegalArgumentException::new);
        var transactionItems = Optional.ofNullable(transactionInfo.transactionItemsList())
                .map(transactionItemsList -> transactionItemsList.stream()
                        .map(StorageItemCreateDto::medicineId)
                        .map(medicineRepository::getReferenceById)
                        .toList())
                .orElseThrow(IllegalArgumentException::new);
        Optional.of(transactionInfo)
                .map(transactionCreateMapper::map)
                .map(transaction -> {
                    transaction.setType(transactionType);
                    transaction.setBranch(branch);
                    transaction.setOrder(order);
                    transaction.setItems(transactionItems);
                    return transaction;
                })
                .map(transactionRepository::save);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR') and " +
            "@SecurityValidation.checkUserBranchAccess(#userDetails, #transactionInfo.branchId)")
    @Transactional
    public List<StorageItemsReadDto> createSaleTransaction(UserDetails userDetails,
                                                           TransactionCreateDto transactionInfo) {
        var transactionType = typeRepository.getReferenceById(TransactionName.SALE.ordinal());
        createTransaction(transactionInfo, transactionType);

        var inventoryRequestDto = new InventoryRequestDto(transactionInfo.branchId(), transactionInfo.transactionItemsList());
        return storageService.createInventorySaleByBranchId(inventoryRequestDto);
    }
}
