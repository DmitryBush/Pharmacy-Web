package com.bush.pharmacy_web_app.model.dto.warehouse;

import java.util.List;

public record TransactionCreateDto(Long branchId,
                                   Long orderId,
                                   List<StorageItemCreateDto> transactionItemsList) {
}
