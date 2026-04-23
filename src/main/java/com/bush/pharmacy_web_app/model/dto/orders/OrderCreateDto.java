package com.bush.pharmacy_web_app.model.dto.orders;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public record OrderCreateDto(@Validated @NotNull List<OrderItemCreateDto> orderItems,
                             @NotNull Long branchId) {
}
