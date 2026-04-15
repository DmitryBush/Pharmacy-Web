package com.bush.pharmacy_web_app.model.dto.orders;

import java.math.BigDecimal;

public record OrderItemCreateDto(Long productId,
                                 Integer quantity,
                                 BigDecimal price) {
}
