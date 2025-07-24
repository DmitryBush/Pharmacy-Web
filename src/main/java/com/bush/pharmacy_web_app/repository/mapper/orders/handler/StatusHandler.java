package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;

public interface StatusHandler {
    StatusHandler setNext(StatusHandler handler);
    String handle(OrderState status);
}
