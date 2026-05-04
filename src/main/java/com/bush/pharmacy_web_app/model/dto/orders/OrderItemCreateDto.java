package com.bush.pharmacy_web_app.model.dto.orders;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemCreateDto(@NotNull Long productId,
                                 @NotNull @Min(0) Integer quantity,
                                 @NotNull BigDecimal price) {
}
