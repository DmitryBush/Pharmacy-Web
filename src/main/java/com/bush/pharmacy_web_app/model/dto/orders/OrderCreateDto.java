package com.bush.pharmacy_web_app.model.dto.orders;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateDto(@NotNull List<OrderItemCreateDto> orderItems,
                             @NotNull Long branchId) {
}
