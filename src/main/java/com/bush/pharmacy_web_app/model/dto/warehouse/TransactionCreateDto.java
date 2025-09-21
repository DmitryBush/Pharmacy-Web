package com.bush.pharmacy_web_app.model.dto.warehouse;

import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionName;

import java.util.List;

public record TransactionCreateDto(TransactionName transactionType,
                                   Long branchId,
                                   Long orderId,
                                   List<StorageItemCreateDto> transactionItemsList) {
}
