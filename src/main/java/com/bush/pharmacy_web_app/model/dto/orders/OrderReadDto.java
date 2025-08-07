package com.bush.pharmacy_web_app.model.dto.orders;

import com.bush.pharmacy_web_app.model.dto.warehouse.PharmacyBranchReadDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderReadDto(Long id,
                           OrderStatusDto statusOrder,
                           Instant date,
                           PharmacyBranchReadDto branch,
                           List<OrderItemReadDto> cartItems,
                           BigDecimal result) {
}
