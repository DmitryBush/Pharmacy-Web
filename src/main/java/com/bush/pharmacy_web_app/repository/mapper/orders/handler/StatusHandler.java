package com.bush.pharmacy_web_app.repository.mapper.orders.handler;

import com.bush.pharmacy_web_app.repository.entity.order.OrderStatus;

public interface StatusHandler {
    void setNext(StatusHandler handler);
    String handle(OrderStatus status);
}
