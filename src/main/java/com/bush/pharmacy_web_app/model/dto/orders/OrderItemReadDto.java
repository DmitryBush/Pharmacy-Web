package com.bush.pharmacy_web_app.model.dto.orders;

import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;

import java.math.BigDecimal;

public record OrderItemReadDto(Long id,
                               Integer amount,
                               BigDecimal price,
                               ProductPreviewReadDto medicine) {
}
