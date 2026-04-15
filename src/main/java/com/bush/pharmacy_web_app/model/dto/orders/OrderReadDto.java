package com.bush.pharmacy_web_app.model.dto.orders;

import com.bush.pharmacy_web_app.model.dto.branch.PharmacyBranchReadDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderReadDto(UUID id,
                           OrderStatusDto statusOrder,
                           Instant date,
                           String userId,
                           PharmacyBranchReadDto branch,
                           List<OrderItemReadDto> cartItems,
                           BigDecimal result) {
}
