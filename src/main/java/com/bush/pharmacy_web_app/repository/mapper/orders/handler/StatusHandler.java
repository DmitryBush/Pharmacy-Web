package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderStatus;

public interface StatusHandler {
    StatusHandler setNext(StatusHandler handler);
    String handle(OrderStatus status);
}
