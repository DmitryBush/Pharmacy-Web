package com.bush.pharmacy_web_app.model.dto.warehouse;

import java.util.List;

public record InventoryReceiptRequestDto(Long branchId, List<StorageItemCreateDto> productList) {
}
