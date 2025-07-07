package com.bush.pharmacy_web_app.repository.dto.orders;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderEvent;

public record OrderStateChangeDto(OrderEvent event) {
}
