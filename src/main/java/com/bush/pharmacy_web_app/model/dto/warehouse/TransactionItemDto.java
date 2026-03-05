package com.bush.pharmacy_web_app.model.dto.warehouse;

import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;

import java.math.BigDecimal;

public record TransactionItemDto(ProductPreviewReadDto medicine,
                                 Integer amount,
                                 BigDecimal price) {
}
