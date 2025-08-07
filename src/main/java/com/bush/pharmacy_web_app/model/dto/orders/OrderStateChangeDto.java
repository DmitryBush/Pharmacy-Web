package com.bush.pharmacy_web_app.model.dto.orders;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderEvent;

public record OrderStateChangeDto(OrderEvent event) {
}
