package com.bush.pharmacy_web_app.model.dto.warehouse;

import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;

public record StorageItemsReadDto(Long id,
                                  Integer amount,
                                  ProductPreviewReadDto medicine) {
}
