package com.bush.pharmacy_web_app.model.dto.warehouse;

import java.util.List;

public record InventoryRequestDto(Long branchId, List<StorageItemCreateDto> productList) {
}
