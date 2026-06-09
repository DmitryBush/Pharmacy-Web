package com.bush.pharmacy_web_app.model.dto.warehouse;

import java.util.List;
import java.util.UUID;

public record TransactionCreateDto(Long branchId,
                                   UUID orderId,
                                   List<StorageItemCreateDto> transactionItemsList) {
}
